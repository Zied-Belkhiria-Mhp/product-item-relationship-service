//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.aaswrapper.registry.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Endpoint
 */
@Data
class Endpoint {

    /**
     * interfaceInformation
     */
    @JsonProperty("interface")
    private String interfaceInformation;
    /**
     * protocolInformation
     */
    private ProtocolInformation protocolInformation;

}
