package com.island.aadhar.util.pojo;

import com.island.aadhar.entity.IDPolicyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyDetail {
    IDPolicyEntity IDPolicyEntity;
    Long currentIdCounter;
}
