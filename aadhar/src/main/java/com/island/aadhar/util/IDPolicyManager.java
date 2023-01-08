package com.island.aadhar.util;

import com.island.aadhar.db.AadharRepository;
import com.island.aadhar.db.DBManager;
import com.island.aadhar.entity.AadharPolicyEntity;
import com.island.aadhar.util.pojo.IDRangeDetails;
import com.island.aadhar.util.pojo.Pair;
import com.island.aadhar.util.pojo.PolicyDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
        refreshPolicyDetailsMap();
    }

    public void addPolicyDetail(AadharPolicyEntity aadharPolicyEntity){
        policyDetailsMap.put(aadharPolicyEntity.getId(), new PolicyDetail(aadharPolicyEntity,aadharPolicyEntity.getCounter()));
    }

    public void removePolicyDetail(Integer id){
        policyDetailsMap.remove(id);
    }

    public void refreshPolicyDetailsMap(){
        List<AadharPolicyEntity> idPolicies = aadharRepository.findAll();
        if(idPolicies == null){
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

    @Transactional(rollbackFor = Exception.class)
    public IDRangeDetails getNextIdForPolicy(Integer policyId, Long batchSize) {
        IDRangeDetails idRangeDetails= new IDRangeDetails();

        synchronized(this) {

            while(batchSize > 0){
                PolicyDetail policyDetail = policyDetailsMap.get(policyId);
                idRangeDetails.setIdType(policyDetail.getAadharPolicyEntity().getIdType());
                Long currentIdCounter = policyDetail.getCurrentIdCounter();
                if (currentIdCounter+batchSize < policyDetail.getAadharPolicyEntity().getCounter()) {
                    setIDRangeDetails(idRangeDetails, new Pair(currentIdCounter, currentIdCounter+batchSize));
                    policyDetail.setCurrentIdCounter(currentIdCounter+batchSize);
                    batchSize = 0L;
                } else {
                    //policyEntityCounter is inclusive
                    Long foundIds = policyDetail.getAadharPolicyEntity().getCounter() - currentIdCounter+1;
                    setIDRangeDetails(idRangeDetails, new Pair(currentIdCounter, currentIdCounter+foundIds));
                    fetchNewSetOfIds(policyId, policyDetail.getAadharPolicyEntity().getCounter() + 1);
                    batchSize -= foundIds;
                }
            }
        }

        return idRangeDetails;
    }

    private PolicyDetail fetchNewSetOfIds(Integer policyId, long counter) {
        AadharPolicyEntity aadharPolicyEntity = dbManager.findById(policyId);
        aadharPolicyEntity.setCounter(aadharPolicyEntity.getCounter()+aadharPolicyEntity.getFetchSize());
        aadharPolicyEntity = aadharRepository.save(aadharPolicyEntity);
        policyDetailsMap.put(aadharPolicyEntity.getId(), new PolicyDetail(aadharPolicyEntity,counter));
        return policyDetailsMap.get(policyId);
    }

    private void setIDRangeDetails(IDRangeDetails idRangeDetails, Pair pair){
        if(idRangeDetails.getIdRanges() == null){
            idRangeDetails.setIdRanges(new ArrayList<>());
        }
        idRangeDetails.getIdRanges().add(pair);
    }
}
