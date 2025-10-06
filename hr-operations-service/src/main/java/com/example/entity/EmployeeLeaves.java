package com.example.entity;

import com.example.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE employee_leaves SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class EmployeeLeaves  extends AbstractPersistable{
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private String reason;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String user;

}
