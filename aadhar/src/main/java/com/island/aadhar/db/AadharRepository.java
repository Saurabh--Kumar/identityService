package com.island.aadhar.db;

import com.island.aadhar.entity.AadharPolicyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AadharRepository extends CrudRepository<AadharPolicyEntity, Integer> {
    Optional<AadharPolicyEntity> findById(Integer id);
    AadharPolicyEntity save(AadharPolicyEntity entity);

    List<AadharPolicyEntity> findAll();
    void deleteById(Integer id);
}
