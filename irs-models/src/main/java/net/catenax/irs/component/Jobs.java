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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * List of Job and relationship to parts
 */
@Schema(description = "Container for a job its relationship and shells.")
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Jobs.JobsBuilder.class)
@AllArgsConstructor
@SuppressWarnings("PMD.ShortClassName")
public class Jobs {

    @Schema(description = "Information and data for the job.", implementation = Job.class)
    private Job job;

    @Schema(description = "Collection of relationships mapping the parent child relationship of AssemblyPartRelationShip aspects.")
    @Singular
    private List<Relationship> relationships;

    @Schema(description = "Collections of AAS shells.")
    private List<Shell> shells;

    @Schema(description = "Collection of not resolvable Endpoints as Tombstones. Including cause of error and endpoint URL.")
    @Singular
    private List<Tombstone> tombstones;

    /**
     * Builder class
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class JobsBuilder {
    }

}
