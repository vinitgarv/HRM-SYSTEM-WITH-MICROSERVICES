package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE ATTENDANCE_LOGS SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class AttendanceLogs extends AbstractPersistable
{
    private String punchType;
    private LocalTime punchTime;
    private LocalDate date;
    private String bmType;
    private String location;

    private String user;
}
