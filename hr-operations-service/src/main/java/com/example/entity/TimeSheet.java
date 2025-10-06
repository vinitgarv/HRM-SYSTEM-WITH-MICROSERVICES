package com.example.entity;

import com.example.dto.response.UserResponse;
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

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance
@SQLDelete(sql = "UPDATE time_sheet SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class TimeSheet extends AbstractPersistable
{
        private LocalDate datePosted;
        private Double hours;
        private String description;
        private String approvedBy;
        private LocalDate approvalDate;
        private String status;

        private String user;
}
