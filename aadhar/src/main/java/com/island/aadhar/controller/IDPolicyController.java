package com.island.aadhar.controller;

import com.island.aadhar.db.AadharRepository;
import com.island.aadhar.entity.AadharPolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/identityPolicy")
public class IDPolicyController {

    @Autowired
    private AadharRepository aadharRepository;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createPolicy(@RequestBody AadharPolicyEntity aadharPolicyEntity){
        try{
            AadharPolicyEntity aadharPolicy = aadharRepository.save(aadharPolicyEntity);
            return ResponseEntity.ok(String.valueOf(aadharPolicy.getId()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{policyId}")
    public ResponseEntity<String> deletePolicy(@PathVariable Integer policyId){
        try{
            aadharRepository.deleteById(policyId);
            return ResponseEntity.ok(String.valueOf(policyId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }
}
