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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import net.catenax.irs.component.IStatusCodeEnum;

/**
 * Http return status code
 */
@Schema(description = "Http return status code")
public enum StatusCodeEnum implements IStatusCodeEnum {

    CONTINUE(100, "CONTINUE"),
    SWITCHING_PROTOCOLS(101, "SWITCHING_PROTOCOLS"),
    PROCESSING(102, "PROCESSING"),
    CHECKPOINT(103, "CHECKPOINT"),
    OK(200, "OK"),
    CREATED(201, "CREATED"),
    ACCEPTED(202, "ACCEPTED"),
    NON_AUTHORITATIVE_INFORMATION(203, "NON_AUTHORITATIVE_INFORMATION"),
    NO_CONTENT(204, "NO_CONTENT"),
    RESET_CONTENT(205, "RESET_CONTENT"),
    PARTIAL_CONTENT(206, "PARTIAL_CONTENT"),
    MULTI_STATUS(207, "MULTI_STATUS"),
    ALREADY_REPORTED(208, "ALREADY_REPORTED"),
    IM_USED(226, "IM_USED"),
    MULTIPLE_CHOICES(300, "MULTIPLE_CHOICES"),
    MOVED_PERMANENTLY(301, "MOVED_PERMANENTLY"),
    FOUND(302, "FOUND"),
    MOVED_TEMPORARILY(302, "MOVED_TEMPORARILY"),
    SEE_OTHER(303, "SEE_OTHER"),
    NOT_MODIFIED(304, "NOT_MODIFIED"),
    USE_PROXY(305, "USE_PROXY"),
    TEMPORARY_REDIRECT(307, "TEMPORARY_REDIRECT"),
    PERMANENT_REDIRECT(308, "PERMANENT_REDIRECT"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    PAYMENT_REQUIRED(402, "PAYMENT_REQUIRED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    NOT_ACCEPTABLE(406, "NOT_ACCEPTABLE"),
    PROXY_AUTHENTICATION_REQUIRED(407, "PROXY_AUTHENTICATION_REQUIRED"),
    REQUEST_TIMEOUT(408, "REQUEST_TIMEOUT"),
    CONFLICT(409, "CONFLICT"),
    GONE(410, "GONE"),
    LENGTH_REQUIRED(411, "LENGTH_REQUIRED"),
    PRECONDITION_FAILED(412, "PRECONDITION_FAILED"),
    PAYLOAD_TOO_LARGE(413, "PAYLOAD_TOO_LARGE"),
    REQUEST_ENTITY_TOO_LARGE(413, "REQUEST_ENTITY_TOO_LARGE"),
    URI_TOO_LONG(414, "URI_TOO_LONG"),
    REQUEST_URI_TOO_LONG(414, "REQUEST_URI_TOO_LONG"),
    UNSUPPORTED_MEDIA_TYPE(415, "UNSUPPORTED_MEDIA_TYPE"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "REQUESTED_RANGE_NOT_SATISFIABLE"),
    EXPECTATION_FAILED(417, "EXPECTATION_FAILED"),
    I_AM_A_TEAPOT(418, "I_AM_A_TEAPOT"),
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "INSUFFICIENT_SPACE_ON_RESOURCE"),
    METHOD_FAILURE(420, "METHOD_FAILURE"),
    DESTINATION_LOCKED(421, "DESTINATION_LOCKED"),
    UNPROCESSABLE_ENTITY(422, "UNPROCESSABLE_ENTITY"),
    LOCKED(423, "LOCKED"),
    FAILED_DEPENDENCY(424, "FAILED_DEPENDENCY"),
    TOO_EARLY(425, "TOO_EARLY"),
    UPGRADE_REQUIRED(426, "UPGRADE_REQUIRED"),
    PRECONDITION_REQUIRED(428, "PRECONDITION_REQUIRED"),
    TOO_MANY_REQUESTS(429, "TOO_MANY_REQUESTS"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "REQUEST_HEADER_FIELDS_TOO_LARGE"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "UNAVAILABLE_FOR_LEGAL_REASONS"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    NOT_IMPLEMENTED(501, "NOT_IMPLEMENTED"),
    BAD_GATEWAY(502, "BAD_GATEWAY"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
    GATEWAY_TIMEOUT(504, "GATEWAY_TIMEOUT"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP_VERSION_NOT_SUPPORTED"),
    VARIANT_ALSO_NEGOTIATES(506, "VARIANT_ALSO_NEGOTIATES"),
    INSUFFICIENT_STORAGE(507, "INSUFFICIENT_STORAGE"),
    LOOP_DETECTED(508, "LOOP_DETECTED"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "BANDWIDTH_LIMIT_EXCEEDED"),
    NOT_EXTENDED(510, "NOT_EXTENDED"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "NETWORK_AUTHENTICATION_REQUIRED");

    @Getter
    Integer code;

    @Getter
    String message;

    StatusCodeEnum(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

}
