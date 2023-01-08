package com.island.aadhar.controller;

import com.island.aadhar.domain.ID;
import com.island.aadhar.domain.response.IDResponse;
import com.island.aadhar.domain.response.StatusResponse;
import com.island.aadhar.domain.response.StatusType;
import com.island.aadhar.exeption.ApplicationException;
import com.island.aadhar.manager.IdentityManager;
import com.island.aadhar.util.enums.IDSuccessCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/identity")
public class IdentityController {

    @Autowired
    private IdentityManager idManager;

    @GetMapping(value = "/getId/{policyId}")
    public ResponseEntity<IDResponse> getId(@PathVariable Integer policyId,
                                          @RequestParam(required = false, defaultValue = "1") Long batchSize){
        try{
            List<ID> idList = idManager.getIdBatchForPolicy(policyId, batchSize);
            return ResponseEntity.ok(
                    new IDResponse(new StatusResponse(IDSuccessCodes.GET_ID_SUCCESS.getSuccessCode(), StatusType.SUCCESS, "SUCCESS"),idList)
            );
        } catch (ApplicationException ex) {
            return ResponseEntity.status(ex.getHttpStatus())
                    .body(new IDResponse(new StatusResponse(ex.getErrorCode(), StatusType.ERROR, ex.getMessage())));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new IDResponse(new StatusResponse(000, StatusType.ERROR, ex.getMessage())));
        }
    }
}
