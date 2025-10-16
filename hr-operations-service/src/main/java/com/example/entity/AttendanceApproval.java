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
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance
@SQLDelete(sql = "UPDATE ATTENDANCE_APPROVAL SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class AttendanceApproval extends AbstractPersistable {
    private String reason;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalTime requestedInTime;
    private LocalTime requestedOutTime;

    private LocalDate requestDate;

    private String user;
}
