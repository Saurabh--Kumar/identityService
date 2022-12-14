package com.island.aadhar.entity;

import com.island.aadhar.util.enums.IDType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "id_policy_entity")
@Getter
@Setter
@NoArgsConstructor
public class IDPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Long counter;

    @Column(nullable = false)
    private Integer fetchSize;

    @Column(nullable = false)
    private IDType idType;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime lastModifiedOn;


    @PrePersist
    public void prePersist() {
        if(counter == null)
            counter = 0L;
    }

}
