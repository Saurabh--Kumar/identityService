package com.island.aadhar.util.enums;

import lombok.Getter;

@Getter
public enum IDSuccessCodes {
    GET_ID_SUCCESS(101),
    CREATE_POLICY_SUCCESS(102),
    DELETE_POLICY_SUCCESS(103)
    ;

    private Integer successCode;

    IDSuccessCodes(Integer successCode) {
        this.successCode = successCode;
    }
}
