package com.island.aadhar.db;

import com.island.aadhar.entity.AadharPolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DBManager {

    @Autowired
    AadharRepository aadharRepository;

    public AadharPolicyEntity findById(Integer id) {
        Optional<AadharPolicyEntity> optional = aadharRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<AadharPolicyEntity> findAll() {
        return aadharRepository.findAll();
    }

    public AadharPolicyEntity save(AadharPolicyEntity aadharPolicyEntity) {
        return aadharRepository.save(aadharPolicyEntity);
    }

    public void deleteById(Integer policyId) {
        aadharRepository.deleteById(policyId);
    }
}
