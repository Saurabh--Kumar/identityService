package com.island.aadhar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StringID extends ID{
    private String id;

    public StringID(String id, String type){
        super(type);
        this.id = id;
    }
}
