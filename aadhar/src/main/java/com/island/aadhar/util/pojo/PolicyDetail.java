package com.island.aadhar.util.pojo;

import com.island.aadhar.entity.AadharPolicyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyDetail {
    AadharPolicyEntity aadharPolicyEntity;
    Long currentIdCounter;
}
