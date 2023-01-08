package com.island.aadhar.controller;

import com.island.aadhar.domain.ID;
import com.island.aadhar.domain.StringID;
import com.island.aadhar.manager.IdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/identity")
public class IdentityController {

    @Autowired
    private IdentityManager idManager;

    @GetMapping(value = "/getId/{policyId}")
    public ResponseEntity<List<ID>> getStringId(@PathVariable Integer policyId,
                                                      @RequestParam(required = false) Long batchSize){
        try{
            if(batchSize == null){
                batchSize = 1L;
            }
            List<ID> id = idManager.getId(policyId, batchSize);
            return ResponseEntity.ok(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
