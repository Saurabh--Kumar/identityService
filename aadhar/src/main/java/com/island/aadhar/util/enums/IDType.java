package com.island.aadhar.util.enums;

import lombok.Getter;

@Getter
public enum IDType {
    String("String"),
    Long("Long");

    private final String type;

    IDType(String type) {
        this.type = type;
    }
}
