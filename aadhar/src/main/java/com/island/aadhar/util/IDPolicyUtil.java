package com.island.aadhar.util;

import com.island.aadhar.domain.request.IDPolicyRequest;
import com.island.aadhar.entity.IDPolicyEntity;
import com.island.aadhar.exeption.ApplicationException;
import com.island.aadhar.util.enums.IDError;
import com.island.aadhar.util.enums.IDType;

public class IDPolicyUtil {

    public static IDPolicyEntity convertRequestToEntity(IDPolicyRequest idPolicyRequest){
        IDPolicyEntity idPolicyEntity = new IDPolicyEntity();
        idPolicyEntity.setDescription(idPolicyRequest.getDescription());
        idPolicyEntity.setIdType(idPolicyRequest.getIdType());
        idPolicyEntity.setFetchSize(idPolicyRequest.getFetchSize());
        return idPolicyEntity;
    }

    public static void validateRequest(IDPolicyRequest idPolicyRequest) throws ApplicationException {
        if(idPolicyRequest.getFetchSize() == null) {
            throw new ApplicationException(IDError.FETCH_SIZE_IS_NULL);
        }
        if(idPolicyRequest.getFetchSize().intValue() == 0) {
            throw new ApplicationException(IDError.FETCH_SIZE_IS_ZERO);
        }
        if(idPolicyRequest.getFetchSize().intValue() < 0) {
            throw new ApplicationException(IDError.FETCH_SIZE_IS_NEGATIVE);
        }
        if(idPolicyRequest.getIdType() == null) {
            throw new ApplicationException(IDError.ID_TYPE_IS_NULL);
        }

        if(!(IDType.String.equals(idPolicyRequest.getIdType())
                || IDType.Long.equals(idPolicyRequest.getIdType()))) {
            throw new ApplicationException(IDError.INVALID_ID_TYPE);
        }
    }
}
