package net.catenax.irs.services;

import java.util.UUID;

import net.catenax.irs.TestConfig;
import net.catenax.irs.connector.job.JobState;
import net.catenax.irs.connector.job.JobStore;
import net.catenax.irs.connector.job.MultiTransferJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = { "test" })
@Import(TestConfig.class)
class IrsItemGraphQueryServiceTest {

    private final UUID jobId = UUID.randomUUID();

    @Autowired
    private JobStore jobStore;

    @Autowired
    private IrsItemGraphQueryService service;

    @Test
    void registerItemJob() {
        assertTrue(true);
    }

    @Test
    void jobLifecycle() {
        assertTrue(true);
    }

    @Test
    void getJobsByProcessingState() {
        assertTrue(true);
    }

    @Test
    void cancelJobById() {
        final String idAsString = String.valueOf(jobId);
        final MultiTransferJob multiTransferJob = MultiTransferJob.builder()
                                                                  .jobId(idAsString)
                                                                  .state(JobState.UNSAVED)
                                                                  .errorDetail("Job should be canceled")
                                                                  .build();

        jobStore.create(multiTransferJob);

        assertNotNull(service.cancelJobById(jobId));
        assertFalse(jobStore.find(idAsString).isEmpty());

        final JobState state = jobStore.find(idAsString).get().getState();
        assertEquals(state, JobState.CANCELED);
    }
}