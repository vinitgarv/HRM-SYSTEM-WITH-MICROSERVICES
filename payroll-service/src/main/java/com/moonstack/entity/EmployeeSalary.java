package com.moonstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE EMPLOYEE_SALARY SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class EmployeeSalary extends AbstractPersistable
{
    private Double baseSalary;
    private Double allowances;
    private Double deductions;
    private Double taxAmounts;
    private Double netSalary;
    private String accountNumber;
    private String userId;

}
