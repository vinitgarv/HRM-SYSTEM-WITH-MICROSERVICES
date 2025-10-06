package com.example.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeLeavesRequest {
    private String leaveType;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
}
