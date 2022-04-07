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

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import net.catenax.irs.annotations.ExcludeFromCodeCoverageGeneratedReport;

/**
 * List of Job and relationship to parts
 */
@Schema(description = "List of Job and relationship to parts")
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Jobs.JobsBuilder.class)
@AllArgsConstructor
@SuppressWarnings("PMD.ShortClassName")
@ExcludeFromCodeCoverageGeneratedReport
public class Jobs {

    @Schema(description = "Information and data for the Job", implementation = Job.JobBuilder.class)
    private Job job;

    @Schema(description = "Parts relationship information")
    @Singular
    private List<Relationship> relationships;

    @Schema
    private Optional<List<Shells>> shells;

    /**
     * Builder class
     */
    @JsonPOJOBuilder(withPrefix = "with")
    public static class JobsBuilder {
    }

}