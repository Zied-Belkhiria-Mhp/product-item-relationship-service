//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.configuration;

import lombok.Data;

/**
 * Configuration settings for retrying incoming event processing.
 */
@Data
public class EventProcessingRetryConfiguration {
    /**
     * Default value for the maximum number of retries.
     */
    private static final int DEFAULT_MAX_RETRIES = 10;

    /**
     * Maximum number of retries.
     */
    private int maxRetries = DEFAULT_MAX_RETRIES;

}
