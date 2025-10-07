package com.example.entity;

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
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance
@SQLDelete(sql = "UPDATE ATTENDANCE_DATA SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class AttendanceData extends AbstractPersistable
{
    private LocalDate date;
    private LocalTime firstPunchInTime;
    private LocalTime lastPunchoutTime;
    private String attendanceStatus;
    private boolean valid;

    private String user;
}
