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
@SQLDelete(sql = "UPDATE dependent_detail SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class DependentDetail extends AbstractPersistable {
    private String name;
    private String relation;
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
