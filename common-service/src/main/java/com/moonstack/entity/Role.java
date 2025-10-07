package com.moonstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance
@SQLDelete(sql = "UPDATE ROLE SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class Role extends AbstractPersistable {
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
