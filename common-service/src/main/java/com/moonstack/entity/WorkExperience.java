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
public class WorkExperience extends AbstractPersistable{

    private String companyName;
    private String jobTitle;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String jobDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
