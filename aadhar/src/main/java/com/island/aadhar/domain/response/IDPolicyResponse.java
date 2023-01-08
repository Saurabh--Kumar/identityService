package com.island.aadhar.domain.response;

import lombok.Data;

@Data
public class IDPolicyResponse extends AbstractResponse{
    private Integer policyId;

    public IDPolicyResponse(StatusResponse statusResponse){
        super(statusResponse);
    }

    public IDPolicyResponse(StatusResponse statusResponse, Integer policyId){
        super(statusResponse);
        this.policyId = policyId;
    }
}
