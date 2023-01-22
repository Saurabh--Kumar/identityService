package com.island.aadhar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LongID extends ID{
    private Long id;

    public LongID(Long id, String type){
        super(type);
        this.id = id;
    }
}
