package com.island.aadhar.domain.request;

import com.island.aadhar.util.enums.IDType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDPolicyRequest {

    private Integer fetchSize;
    private IDType idType;
    private String description;
}
