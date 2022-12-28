package com.island.aadhar.controller;

import com.island.aadhar.manager.IdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/identity")
public class IdentityController {

    @Autowired
    private IdentityManager idManager;

    @GetMapping(value = "/getId/{policyId}")
    public ResponseEntity<String> getStringId(@PathVariable Integer policyId){
        try{
            String id = idManager.getId(policyId);
            return ResponseEntity.ok(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }
}
