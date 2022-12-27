package com.island.aadhar.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class IDUtil {

    private List<String> characterList = CharacterSetReader.getCharacterList();
    int base = characterList.size();

    private String getStringId(Long number) {
        StringBuilder id = new StringBuilder();

        do{
            String digit = characterList.get((int) (number%base));
            id.append(digit);
            number = number/base;
        } while(number>0);

        return id.reverse().toString();

    }

    public String getIdFromPolicyDetails(PolicyDetail policyDetail) {
        if(IDType.Integer.equals(policyDetail.getAadharPolicyEntity().getIdType())){
            return String.valueOf(policyDetail.getCurrentIdCounter());
        } else {
            return getStringId(policyDetail.getCurrentIdCounter());
        }
    }
}
