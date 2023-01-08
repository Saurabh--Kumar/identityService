package com.island.aadhar.manager;

import com.island.aadhar.domain.ID;
import com.island.aadhar.domain.StringID;
import com.island.aadhar.util.IDPolicyManager;
import com.island.aadhar.util.IDUtil;
import com.island.aadhar.util.pojo.IDRangeDetails;
import com.island.aadhar.util.pojo.PolicyDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class IdentityManager {

    @Autowired
    private IDPolicyManager idPolicyManager;
    @Autowired
    private IDUtil idUtil;

    public List<ID> getId(Integer policyId, Long batchSize) throws Exception{
        IDRangeDetails idRangeDetails = idPolicyManager.getNextIdForPolicy(policyId, batchSize);
        return idUtil.getIdFromPolicyDetails(idRangeDetails);
    }
}
