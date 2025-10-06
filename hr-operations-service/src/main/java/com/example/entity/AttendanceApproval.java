package com.example.entity;


import com.example.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance
public class AttendanceApproval extends AbstractPersistable {
    private String reason;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalTime requestedInTime;
    private LocalTime requestedOutTime;

    private LocalDate requestDate;

    private String user;
}
