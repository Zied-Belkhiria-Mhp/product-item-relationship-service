//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/***
 * API type for the view of the parts tree to be returned by a query.
 *
 * @see ItemLifecycleStage
 */
@Schema(description = "View defining which data of the PartsTree is retrieved.")
public enum ItemsTreeView {
    @Schema(description = "The view of the PartsTree as the vehicle was assembled.") AS_BUILT,

    @Schema(description = "The view of the PartsTree that accounts for all updates during the vehicle lifecycle.") AS_MAINTAINED
}
