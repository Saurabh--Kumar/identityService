package com.island.aadhar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ID {
    public String idType;

    public ID(String type) {
        this.idType = type;
    }
}
