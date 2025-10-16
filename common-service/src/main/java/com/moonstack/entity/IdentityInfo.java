package com.moonstack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@SuperBuilder
@Inheritance
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE identity_info SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class IdentityInfo extends AbstractPersistable{

    private String documentType;
    private String fileName;
    private String fileType;
    private String documentNumber;
    private String status;
    private boolean verified;
    private String verifiedBy;
    private LocalDate verificationDate;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
