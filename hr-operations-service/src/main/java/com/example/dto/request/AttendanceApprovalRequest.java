package com.example.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceApprovalRequest {
    private String userId;
    private LocalDate requestDate;
    private String reason;
    private LocalTime inTime;
    private LocalTime outTime;
}
