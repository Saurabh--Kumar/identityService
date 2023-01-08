package com.island.aadhar.util;

import com.island.aadhar.domain.ID;
import com.island.aadhar.domain.LongID;
import com.island.aadhar.domain.StringID;
import com.island.aadhar.util.enums.IDType;
import com.island.aadhar.util.pojo.IDRangeDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

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

    public List<ID> getIdFromPolicyDetails(IDRangeDetails idRangeDetails) {

        List<ID> idList = new ArrayList<>();
        if(IDType.Long.equals(idRangeDetails.getIdType())) {
            idRangeDetails.getIdRanges().stream().forEach(
                    pair -> LongStream.range(pair.getStart(), pair.getEnd()).forEach(n -> {
                        idList.add(new LongID(n,IDType.Long.name()));
                    })
            );
        } else {
            idRangeDetails.getIdRanges().stream().forEach(
                    pair -> LongStream.range(pair.getStart(), pair.getEnd()).forEach(n -> {
                        idList.add(new StringID(getStringId(n),IDType.String.name()));
                    })
            );
        }

        return idList;
    }

}
