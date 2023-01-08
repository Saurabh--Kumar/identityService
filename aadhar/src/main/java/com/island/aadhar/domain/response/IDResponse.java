package com.island.aadhar.domain.response;

import com.island.aadhar.domain.ID;
import lombok.Data;

import java.util.List;

@Data
public class IDResponse extends AbstractResponse{
    private List<ID> idList;

    public IDResponse(StatusResponse statusResponse){
        super(statusResponse);
    }

    public IDResponse(StatusResponse statusResponse, List<ID> idList){
        super(statusResponse);
        this.idList = idList;
    }
}
