package com.island.aadhar.entity;

import com.island.aadhar.util.IDType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AadharPolicyEntity {
    @Id
    private Integer id;
    private Long counter;
    private int fetchSize;
    private IDType idType;

}
