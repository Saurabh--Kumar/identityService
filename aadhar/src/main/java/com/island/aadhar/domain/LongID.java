package com.island.aadhar.domain;

public class LongID extends ID{
    private Long id;

    public LongID(Long id, String type){
        super(type);
        this.id = id;
    }
}
