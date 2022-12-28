package com.island.aadhar.entity;

import com.island.aadhar.util.IDType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class AadharPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private long counter;
    private Integer fetchSize;
    private IDType idType;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date lastModifiedOn;

    public AadharPolicyEntity(Integer fetchSize, IDType idType){
        this.fetchSize = fetchSize;
        this.idType = idType;
    }

}
