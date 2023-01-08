package com.island.aadhar.util.pojo;

import com.island.aadhar.util.enums.IDType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDRangeDetails {
    List<Pair> idRanges;
    IDType idType;
}
