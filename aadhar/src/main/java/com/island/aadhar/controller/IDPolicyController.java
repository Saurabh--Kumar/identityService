package com.island.aadhar.controller;

import com.island.aadhar.db.DBManager;
import com.island.aadhar.domain.request.IDPolicyRequest;
import com.island.aadhar.domain.response.IDPolicyResponse;
import com.island.aadhar.domain.response.StatusResponse;
import com.island.aadhar.domain.response.StatusType;
import com.island.aadhar.entity.IDPolicyEntity;
import com.island.aadhar.exeption.ApplicationException;
import com.island.aadhar.util.IDPolicyManager;
import com.island.aadhar.util.IDPolicyUtil;
import com.island.aadhar.util.enums.IDSuccessCodes;
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
    private DBManager dbManager;

    @Autowired
    private IDPolicyManager idPolicyManager;

    @PostMapping(value = "/create")
    public ResponseEntity<IDPolicyResponse> createPolicy(@RequestBody IDPolicyRequest idPolicyRequest){
        try{
            IDPolicyUtil.validateRequest(idPolicyRequest);
            IDPolicyEntity idPolicyEntity = IDPolicyUtil.convertRequestToEntity(idPolicyRequest);
            IDPolicyEntity idPolicy = dbManager.save(idPolicyEntity);
            idPolicyManager.addPolicyDetail(idPolicy);
            return ResponseEntity.ok(
                    new IDPolicyResponse(new StatusResponse(IDSuccessCodes.CREATE_POLICY_SUCCESS.getSuccessCode(), StatusType.SUCCESS, "SUCCESS"), idPolicy.getId())
            );
        } catch (ApplicationException ex) {
            return ResponseEntity.status(
                            ex.getHttpStatus())
                    .body(new IDPolicyResponse(new StatusResponse(ex.getErrorCode(), StatusType.ERROR, ex.getMessage())));
        } catch (Exception ex) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new IDPolicyResponse(new StatusResponse(000, StatusType.ERROR, ex.getMessage())));
        }
    }

    //TODO- add a refresh api


    @DeleteMapping(value = "/delete/{policyId}")
    public ResponseEntity<IDPolicyResponse> deletePolicy(@PathVariable Integer policyId){
        try{
            dbManager.deleteById(policyId);
            idPolicyManager.removePolicyDetail(policyId);
            return ResponseEntity.ok(
                    new IDPolicyResponse(new StatusResponse(IDSuccessCodes.DELETE_POLICY_SUCCESS.getSuccessCode(), StatusType.SUCCESS, "SUCCESS"), policyId)
            );
        } catch (ApplicationException ex) {
            return ResponseEntity.status(
                            ex.getHttpStatus())
                    .body(new IDPolicyResponse(new StatusResponse(ex.getErrorCode(), StatusType.ERROR, ex.getMessage())));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new IDPolicyResponse(new StatusResponse(000, StatusType.ERROR, ex.getMessage())));
        }
    }
}
