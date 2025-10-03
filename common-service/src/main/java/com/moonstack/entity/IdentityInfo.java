package com.moonstack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

    private String uan;
    private String pan;
    private String aadhar;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
