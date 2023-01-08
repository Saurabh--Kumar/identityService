package com.island.aadhar.db;

import com.island.aadhar.entity.IDPolicyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IDRepository extends CrudRepository<IDPolicyEntity, Integer> {
    Optional<IDPolicyEntity> findById(Integer id);
    IDPolicyEntity save(IDPolicyEntity entity);

    List<IDPolicyEntity> findAll();
    void deleteById(Integer id);
}
