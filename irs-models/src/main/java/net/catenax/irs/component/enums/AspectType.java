//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.irs.component.enums;

import net.catenax.irs.annotations.ExcludeFromCodeCoverageGeneratedReport;

/**
 * AspectType information for a part tree
 */
@SuppressWarnings("PMD.ShortMethodName")
@ExcludeFromCodeCoverageGeneratedReport
public enum AspectType {
    SERIAL_PART_TYPIZATION("SerialPartTypization"),
    ASSEMBLY_PART_RELATIONSHIP("AssemblyPartRelationship"),
    PART_DIMENSION("PartDimension"),
    SUPPLY_RELATION_DATA("SupplyRelationData"),
    PCF_CORE_DATA("PCFCoreData"),
    PCF_TECHNICAL_DATA("PCFTechnicalData"),
    MARKET_PLACE_OFFER("MarketPlaceOffer"),
    MATERIAL_ASPECT("MaterialAspect"),
    BATTERY_PASS("BatteryPass"),
    PRODUCT_DESCRIPTION_VEHICLE("ProductDescriptionVehicle"),
    PRODUCT_DESCRIPTION_BATTERY("ProductDescriptionBattery"),
    RETURN_REQUEST("ReturnRequest"),
    CERTIFICATION_OF_DESTRUCTION("CertificateOfDestruction"),
    CERTIFICATE_OF_DISMANTLER("CertificateOfDismantler"),
    ADDRESS("Address"),
    CONTACT("Contact");

    private final String value;

    AspectType(final String value) {
        this.value = value;
    }

    /**
     * of as a substitute/alias for valueOf handling the default value
     *
     * @param value see {@link #value}
     * @return the corresponding AspectType
     */
    public static AspectType value(final String value) {
        return AspectType.valueOf(value);
    }

    /**
     * @return convert AspectType to string value
     */
    @Override
    public String toString() {
        return value;
    }

}