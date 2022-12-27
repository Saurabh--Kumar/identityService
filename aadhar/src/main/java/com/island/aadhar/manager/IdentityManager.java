package com.island.aadhar.manager;

import com.island.aadhar.util.IDPolicyManager;
import com.island.aadhar.util.IDUtil;
import com.island.aadhar.util.PolicyDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IdentityManager {

    @Autowired
    private IDPolicyManager idPolicyManager;
    @Autowired
    private IDUtil idUtil;

    public String getId(Integer policyId) throws Exception{
        PolicyDetail policyDetail = idPolicyManager.getNextIdForPolicy(policyId);
        return idUtil.getIdFromPolicyDetails(policyDetail);
    }
}
