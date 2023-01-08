package com.island.aadhar.db;

import com.island.aadhar.entity.IDPolicyEntity;
import com.island.aadhar.exeption.ApplicationException;
import com.island.aadhar.util.enums.IDError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DBManager {

    @Autowired
    IDRepository idRepository;

    public IDPolicyEntity findById(Integer id) {
        Optional<IDPolicyEntity> optional = idRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<IDPolicyEntity> findAll() {
        return idRepository.findAll();
    }

    public IDPolicyEntity save(IDPolicyEntity IDPolicyEntity) {
        return idRepository.save(IDPolicyEntity);
    }

    public void deleteById(Integer policyId) throws ApplicationException {
        try {
            idRepository.deleteById(policyId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ApplicationException(IDError.POLICY_ID_NOT_FOUND);
        }
    }
}
