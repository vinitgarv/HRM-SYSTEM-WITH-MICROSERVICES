package com.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LeaveResponse {
    private String id;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private String reason;
    private String status;
}
