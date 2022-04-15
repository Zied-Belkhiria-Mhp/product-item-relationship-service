//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.aaswrapper.job;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.catenax.irs.aaswrapper.registry.domain.DigitalTwinRegistryFacade;
import net.catenax.irs.aaswrapper.submodel.domain.SubmodelFacade;
import net.catenax.irs.connector.job.ResponseStatus;
import net.catenax.irs.connector.job.TransferInitiateResponse;
import net.catenax.irs.connector.job.TransferProcessManager;
import net.catenax.irs.dto.AssemblyPartRelationshipDTO;
import net.catenax.irs.dto.ChildDataDTO;
import net.catenax.irs.dto.SubmodelEndpoint;
import net.catenax.irs.persistence.BlobPersistence;
import net.catenax.irs.persistence.BlobPersistenceException;
import net.catenax.irs.util.JsonUtil;

/**
 * Process manager for AAS Object transfers.
 * Communicates with the AAS Wrapper.
 */
@Slf4j
@SuppressWarnings("PMD.DoNotUseThreads") // We want to use threads at the moment ;-)
public class AASTransferProcessManager implements TransferProcessManager<ItemDataRequest, AASTransferProcess> {

    private final DigitalTwinRegistryFacade registryFacade;

    private final SubmodelFacade submodelFacade;
    private final ExecutorService executor;

    private final BlobPersistence blobStore;

    public AASTransferProcessManager(final DigitalTwinRegistryFacade registryFacade,
            final SubmodelFacade submodelFacade, final ExecutorService executor, final BlobPersistence blobStore) {
        this.registryFacade = registryFacade;
        this.submodelFacade = submodelFacade;
        this.executor = executor;
        this.blobStore = blobStore;
    }

    @Override
    public TransferInitiateResponse initiateRequest(final ItemDataRequest dataRequest,
            final Consumer<AASTransferProcess> completionCallback) {
        final String processId = UUID.randomUUID().toString();

        executor.submit(getRunnable(dataRequest, completionCallback, processId));

        return new TransferInitiateResponse(processId, ResponseStatus.OK);
    }

    private Runnable getRunnable(final ItemDataRequest dataRequest,
            final Consumer<AASTransferProcess> transferProcessCompleted, final String processId) {
        return () -> {
            final AASTransferProcess aasTransferProcess = new AASTransferProcess(processId);

            final String itemId = dataRequest.getItemId();
            log.info("Calling Digital Twin Registry with itemId {}", itemId);
            final List<SubmodelEndpoint> aasSubmodelEndpoints = registryFacade.getAASSubmodelEndpoints(itemId);
            log.info("Retrieved {} SubmodelEndpoints for itemId {}", aasSubmodelEndpoints.size(), itemId);

            final ItemContainer itemContainer = new ItemContainer();

            aasSubmodelEndpoints.stream()
                                .map(SubmodelEndpoint::getAddress)
                                .map(submodelFacade::getSubmodel)
                                .forEach(submodel -> processEndpoint(aasTransferProcess, itemContainer, submodel));

            try {
                final JsonUtil jsonUtil = new JsonUtil();
                blobStore.putBlob(processId, jsonUtil.asString(itemContainer).getBytes(StandardCharsets.UTF_8));
            } catch (BlobPersistenceException e) {
                log.error("Unable to store AAS result", e);
            }
            transferProcessCompleted.accept(aasTransferProcess);
        };
    }

    private void processEndpoint(final AASTransferProcess aasTransferProcess, final ItemContainer itemContainer,
            final AssemblyPartRelationshipDTO relationship) {
        log.info("Processing AssemblyPartRelationship with {} children", relationship.getChildParts().size());
        final List<String> childIds = relationship.getChildParts()
                                                  .stream()
                                                  .map(ChildDataDTO::getChildCatenaXId)
                                                  .collect(Collectors.toList());
        aasTransferProcess.addIdsToProcess(childIds);
        // TODO (jkreutzfeld) what do we actually need to store here?
        itemContainer.add(relationship);
    }
}
