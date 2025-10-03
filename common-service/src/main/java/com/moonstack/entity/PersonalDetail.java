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
@SQLDelete(sql = "UPDATE personal_detail SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class PersonalDetail extends AbstractPersistable {

    private LocalDate dob;
    private String maritalStatus;
    private String aboutMe;
    private String expertise;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
