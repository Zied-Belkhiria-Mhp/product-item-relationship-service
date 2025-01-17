//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.component;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;
import net.catenax.irs.annotations.ExcludeFromCodeCoverageGeneratedReport;

import java.util.UUID;

/**
 * The unique jobId handle of the just processed job.
 */
@ApiModel(description = "The unique jobId handle of the just processed job.")
@Value
@Builder(toBuilder = true)
@ExcludeFromCodeCoverageGeneratedReport
public class JobHandle {

    private UUID jobId;

    @Override
    public String toString() {
        return jobId.toString();
    }
}
