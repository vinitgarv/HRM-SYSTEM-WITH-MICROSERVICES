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
@SQLDelete(sql = "UPDATE work_info SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class WorkInfo extends AbstractPersistable {

    private String department;
    private String role;
    private String location;
    private String empType;
    private String designation;
    private String empStatus;
    private String sourceOfHire;
    private LocalDate dateOfJoining;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
