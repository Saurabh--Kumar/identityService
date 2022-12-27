package com.island.aadhar.util;

import com.island.aadhar.db.AadharRepository;
import com.island.aadhar.db.DBManager;
import com.island.aadhar.entity.AadharPolicyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IDPolicyManager {

    private Map<Integer, PolicyDetail> policyDetailsMap;


    @Autowired
    private DBManager dbManager;
    @Autowired
    private AadharRepository aadharRepository;

    @PostConstruct
    private void init(){
        dbManager.init();
        List<AadharPolicyEntity> idPolicies = aadharRepository.findAll();
        if(idPolicies == null || idPolicies.size() ==0){
            throw new RuntimeException("Couldn't read existing counter from DB");
        }

        policyDetailsMap = getPolicyDetailsMap(idPolicies);
    }

    private Map<Integer, PolicyDetail> getPolicyDetailsMap(List<AadharPolicyEntity> idPolicies) {
        Map<Integer, PolicyDetail> policyDetailsMap = new HashMap<>();
        for (AadharPolicyEntity aadharPolicyEntity : idPolicies) {
            policyDetailsMap.put(aadharPolicyEntity.getId(), new PolicyDetail(aadharPolicyEntity,aadharPolicyEntity.getCounter()));
        }
        return policyDetailsMap;
    }

    public PolicyDetail getNextIdForPolicy(Integer policyId) {
        synchronized(this) {
            PolicyDetail policyDetail = policyDetailsMap.get(policyId);
            Long currentIdCounter = policyDetail.getCurrentIdCounter();
            if (currentIdCounter < policyDetail.getAadharPolicyEntity().getCounter()) {
                policyDetail.setCurrentIdCounter(currentIdCounter++);
            } else {
                AadharPolicyEntity aadharPolicyEntity = dbManager.findById(policyId);
                policyDetailsMap.put(aadharPolicyEntity.getId(), new PolicyDetail(aadharPolicyEntity,currentIdCounter));
            }
            return policyDetailsMap.get(policyId);
        }
    }
}
