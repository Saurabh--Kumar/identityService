package com.island.aadhar.util;

import com.island.aadhar.db.DBManager;
import com.island.aadhar.entity.IDPolicyEntity;
import com.island.aadhar.exeption.ApplicationException;
import com.island.aadhar.util.enums.IDError;
import com.island.aadhar.util.pojo.IDBatchDetails;
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

    @PostConstruct
    private void init(){
        refreshPolicyDetailsMap();
    }

    public void addPolicyDetail(IDPolicyEntity IDPolicyEntity){
        policyDetailsMap.put(IDPolicyEntity.getId(), new PolicyDetail(IDPolicyEntity, IDPolicyEntity.getCounter()));
    }

    public void removePolicyDetail(Integer id) throws ApplicationException{
        if(!policyDetailsMap.containsKey(id)){
            throw new ApplicationException(IDError.POLICY_ID_NOT_FOUND);
        }
        policyDetailsMap.remove(id);
    }

    public void refreshPolicyDetailsMap(){
        List<IDPolicyEntity> idPolicies = dbManager.findAll();
        if(idPolicies == null){
            throw new RuntimeException("Couldn't read existing counter from DB");
        }

        policyDetailsMap = getPolicyDetailsMap(idPolicies);
    }

    private Map<Integer, PolicyDetail> getPolicyDetailsMap(List<IDPolicyEntity> idPolicies) {
        Map<Integer, PolicyDetail> policyDetailsMap = new HashMap<>();
        for (IDPolicyEntity IDPolicyEntity : idPolicies) {
            policyDetailsMap.put(IDPolicyEntity.getId(), new PolicyDetail(IDPolicyEntity, IDPolicyEntity.getCounter()));
        }
        return policyDetailsMap;
    }

    public IDBatchDetails getIDBatchDetailsForPolicy(Integer policyId, Long batchSize) {
        IDBatchDetails idBatchDetails = new IDBatchDetails();

        synchronized(this) {
            while(batchSize > 0){
                PolicyDetail policyDetail = policyDetailsMap.get(policyId);
                idBatchDetails.setIdType(policyDetail.getIDPolicyEntity().getIdType());
                Long currentIdCounter = policyDetail.getCurrentIdCounter();
                if (currentIdCounter+batchSize < policyDetail.getIDPolicyEntity().getCounter()) {
                    setIDRangeDetails(idBatchDetails, new Pair(currentIdCounter, currentIdCounter+batchSize));
                    policyDetail.setCurrentIdCounter(currentIdCounter+batchSize);
                    batchSize = 0L;
                } else {
                    Long foundIds = policyDetail.getIDPolicyEntity().getCounter() - currentIdCounter+1;
                    setIDRangeDetails(idBatchDetails, new Pair(currentIdCounter, currentIdCounter+foundIds));
                    fetchNewSetOfIds(policyId, policyDetail.getIDPolicyEntity().getCounter() + 1);
                    batchSize -= foundIds;
                }
            }
        }

        return idBatchDetails;
    }

    @Transactional(rollbackFor = Exception.class)
    public PolicyDetail fetchNewSetOfIds(Integer policyId, long counter) {
        IDPolicyEntity IDPolicyEntity = dbManager.findById(policyId);
        IDPolicyEntity.setCounter(IDPolicyEntity.getCounter()+ IDPolicyEntity.getFetchSize());
        IDPolicyEntity = dbManager.save(IDPolicyEntity);
        policyDetailsMap.put(IDPolicyEntity.getId(), new PolicyDetail(IDPolicyEntity,counter));
        return policyDetailsMap.get(policyId);
    }

    private void setIDRangeDetails(IDBatchDetails idBatchDetails, Pair pair){
        if(idBatchDetails.getIdRanges() == null){
            idBatchDetails.setIdRanges(new ArrayList<>());
        }
        idBatchDetails.getIdRanges().add(pair);
    }
}
