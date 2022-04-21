//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.connector.job;

import static java.util.UUID.randomUUID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * Orchestrator service for recursive {@link MultiTransferJob}s that potentially
 * comprise multiple transfers.
 */
@SuppressWarnings("PMD.AvoidCatchingGenericException") // Handle RuntimeException from callbacks
@Slf4j
public class JobOrchestrator<T extends DataRequest, P extends TransferProcess> {

    private static final int TTL_CLEANUP_COMPLETED_JOBS_HOURS = 1;
    private static final int TTL_CLEANUP_FAILED_JOBS_HOURS = 24;

    /**
     * Transfer process manager.
     */
    private final TransferProcessManager<T, P> processManager;

    /**
     * Job store.
     */
    private final JobStore jobStore;

    /**
     * Job handler containing the logic to start transfers and process
     * transfer results.
     */
    private final RecursiveJobHandler<T, P> handler;

    /**
     * Create a new instance of {@link JobOrchestrator}.
     *
     * @param processManager the process manager
     * @param jobStore       Job store.
     * @param handler        Recursive job handler.
     */
    public JobOrchestrator(final TransferProcessManager<T, P> processManager, final JobStore jobStore,
            final RecursiveJobHandler<T, P> handler) {
        this.processManager = processManager;
        this.jobStore = jobStore;
        this.handler = handler;
    }

    /**
     * Start a job.
     *
     * @param jobData additional data for the job to managed by the {@link JobStore}.
     * @return response.
     */
    public JobInitiateResponse startJob(final Map<String, String> jobData) {
        final var job = MultiTransferJob.builder()
                                        .jobId(randomUUID().toString())
                                        .jobData(jobData)
                                        .state(JobState.UNSAVED)
                                        .build();

        jobStore.create(job);

        final Stream<T> requests;
        try {
            requests = handler.initiate(job);
        } catch (RuntimeException e) {
            markJobInError(job, e, "Handler method failed");
            return JobInitiateResponse.builder().jobId(job.getJobId()).status(ResponseStatus.FATAL_ERROR).build();
        }

        long transferCount;
        try {
            transferCount = startTransfers(job, requests);
        } catch (JobException e) {
            return JobInitiateResponse.builder().jobId(job.getJobId()).status(e.getStatus()).build();
        }

        // If no transfers are requested, job is already complete
        if (transferCount == 0) {
            completeJob(job);
        }

        return JobInitiateResponse.builder().jobId(job.getJobId()).status(ResponseStatus.OK).build();
    }

    /**
     * Callback invoked when a transfer has completed.
     *
     * @param process the process that has completed
     */
    /* package */ void transferProcessCompleted(final P process) {
        final var jobEntry = jobStore.findByProcessId(process.getId());
        if (jobEntry.isEmpty()) {
            log.error("Job not found for transfer {}", process.getId());
            return;
        }
        final var job = jobEntry.get();

        if (job.getState() != JobState.IN_PROGRESS) {
            log.info("Ignoring transfer complete event for job {} in state {}", job.getJobId(), job.getState());
            return;
        }

        final Stream<T> requests;
        try {
            requests = handler.recurse(job, process);
        } catch (RuntimeException e) {
            markJobInError(job, e, "Handler method failed");
            return;
        }

        try {
            startTransfers(job, requests);
        } catch (JobException e) {
            markJobInError(job, e, "Failed to start a transfer");
            return;
        }

        jobStore.completeTransferProcess(job.getJobId(), process);

        callCompleteHandlerIfFinished(job.getJobId());
    }

    public List<MultiTransferJob> findAndCleanupCompletedJobs() {
        final LocalDateTime currentDateMinusHours = LocalDateTime.now().minusHours(TTL_CLEANUP_COMPLETED_JOBS_HOURS);
        final List<MultiTransferJob> completedJobs = jobStore.findByStateAndCompletionDateOlderThan(JobState.COMPLETED,
                currentDateMinusHours);

        return deleteJobs(completedJobs);
    }

    public List<MultiTransferJob> findAndCleanupFailedJobs() {
        final LocalDateTime currentDateMinusHours = LocalDateTime.now().minusHours(TTL_CLEANUP_FAILED_JOBS_HOURS);
        final List<MultiTransferJob> failedJobs = jobStore.findByStateAndCompletionDateOlderThan(JobState.ERROR,
                currentDateMinusHours);

        return deleteJobs(failedJobs);
    }

    private List<MultiTransferJob> deleteJobs(final List<MultiTransferJob> jobs) {
        return jobs.stream()
                   .map(job -> jobStore.deleteJob(job.getJobId()))
                   .flatMap(Optional::stream)
                   .collect(Collectors.toList());
    }

    private void callCompleteHandlerIfFinished(final String jobId) {
        jobStore.find(jobId).ifPresent(job -> {
            if (job.getState() != JobState.TRANSFERS_FINISHED) {
                return;
            }
            completeJob(job);
        });
    }

    private void completeJob(final MultiTransferJob job) {
        try {
            handler.complete(job);
        } catch (RuntimeException e) {
            markJobInError(job, e, "Handler method failed");
            return;
        }
        jobStore.completeJob(job.getJobId());
    }

    private void markJobInError(final MultiTransferJob job, final Throwable exception, final String message) {
        log.error(message, exception);
        jobStore.markJobInError(job.getJobId(), message);
    }

    private long startTransfers(final MultiTransferJob job, final Stream<T> dataRequests) /* throws JobException */ {
        return dataRequests.map(r -> startTransfer(job, r)).collect(Collectors.counting());
    }

    private TransferInitiateResponse startTransfer(final MultiTransferJob job,
            final T dataRequest)  /* throws JobException */ {
        final var response = processManager.initiateRequest(dataRequest,
                transferId -> jobStore.addTransferProcess(job.getJobId(), transferId), this::transferProcessCompleted);

        if (response.getStatus() != ResponseStatus.OK) {
            throw JobException.builder().status(response.getStatus()).build();
        }

        return response;
    }

    /**
     * Exception used to stop creating additional transfers if one transfer creation fails.
     */
    @Value
    @Builder
    private static class JobException extends RuntimeException {
        /**
         * The status of the transfer in error.
         */
        private final ResponseStatus status;
    }
}
