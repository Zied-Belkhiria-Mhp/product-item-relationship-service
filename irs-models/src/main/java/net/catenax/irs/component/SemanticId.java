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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import net.catenax.irs.annotations.ExcludeFromCodeCoverageGeneratedReport;

/**
 * SemanticId
 */
@Value
@Builder(toBuilder = true)
@Schema(description = "")
@AllArgsConstructor
@ExcludeFromCodeCoverageGeneratedReport
@JsonDeserialize(builder = SemanticId.SemanticIdBuilder.class)
public class SemanticId {

    @Schema()
    @Singular
    private List<String> values;
    /**
     * User to build SemanticId
     */
    @Schema(description = "User to build async fetched items")
    @JsonPOJOBuilder(withPrefix = "with")
    public static class SemanticIdBuilder {
    }
}
