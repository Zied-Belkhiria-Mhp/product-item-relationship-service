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

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import net.catenax.irs.component.enums.JobState;
import org.jetbrains.annotations.Nullable;

/**
 * Manages storage of {@link MultiTransferJob} state.
 */
public interface JobStore {
    /**
     * Retrieve a job by its identifier.
     *
     * @param jobId the identifier of the job to retrieve.
     * @return the job if found, otherwise empty.
     * @see MultiTransferJob#getJob()
     */
    Optional<MultiTransferJob> find(String jobId);

    /**
     * Retrieve jobs by state with completion date older than requested date
     *
     * @param jobState the job state
     * @param dateTime requested date
     * @return found jobs
     */
    List<MultiTransferJob> findByStateAndCompletionDateOlderThan(JobState jobState, Instant dateTime);

    /**
     * Retrieve a job given a transfer id. Only retrieves jobs
     * for which the transfer has not been completed
     * with {@link #completeTransferProcess(String, TransferProcess)}.
     *
     * @param processId the transfer process identifier.
     * @return the job if found, otherwise empty.
     */
    Optional<MultiTransferJob> findByProcessId(String processId);

    /**
     * Create a job.
     *
     * @param job the job to create and manage.
     */
    void create(MultiTransferJob job);

    /**
     * Add a transfer process identifier to a job.
     *
     * @param jobId     the job identifier.
     * @param processId identifier of the transfer process to attach.
     */
    void addTransferProcess(String jobId, String processId);

    /**
     * Mark transfer process completed for the job.
     *
     * @param jobId     the job identifier.
     * @param processId identifier of the transfer process to mark completed.
     */
    void completeTransferProcess(String jobId, TransferProcess processId);

    /**
     * Mark job as completed.
     *
     * @param jobId the job identifier.
     * @see JobState#COMPLETED
     */
    void completeJob(String jobId);

    /**
     * Mark job as in error.
     *
     * @param jobId       the job identifier.
     * @param errorDetail an optional error message.
     * @see JobState#ERROR
     */
    void markJobInError(String jobId, @Nullable String errorDetail);

    /**
     * Delete a job by its identifier.
     *
     * @param jobId the job identifier.
     * @return deleted job
     */
    MultiTransferJob deleteJob(String jobId);

    /**
     * Get and return the current job state
     *
     * @param jobId the job identifier
     * @return job state
     */
    JobState getJobState(String jobId);
}
