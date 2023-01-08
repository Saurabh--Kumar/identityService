package com.island.aadhar.util.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum IDError {

    POLICY_ID_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "Policy not found for given ID"),

    FETCH_SIZE_IS_ZERO(HttpStatus.BAD_REQUEST, 1002, "fetchSize for a policy can't be 0"),
    FETCH_SIZE_IS_NEGATIVE(HttpStatus.BAD_REQUEST, 1003, "fetchSize for a policy can't be negative"),
    FETCH_SIZE_IS_NULL(HttpStatus.BAD_REQUEST, 1004, "fetchSize is null"),

    INVALID_ID_TYPE(HttpStatus.BAD_REQUEST, 1005, "idType is not valid"),
    ID_TYPE_IS_NULL(HttpStatus.BAD_REQUEST, 1006, "idType is null");


    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String errorMessage;

    IDError(HttpStatus httpStatus, Integer errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
