package com.island.aadhar.db;

import com.island.aadhar.entity.AadharPolicyEntity;
import com.island.aadhar.util.IDType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBManager {

    private int batchSize = 50;

    @Autowired
    AadharRepository aadharRepository;

    public AadharPolicyEntity findById(Integer id) {
        Optional<AadharPolicyEntity> optional = aadharRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void init(){
        aadharRepository.save(new AadharPolicyEntity(0,1000000000000000000L, 100, IDType.Sting));
    }
}