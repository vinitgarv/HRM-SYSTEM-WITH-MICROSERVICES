package com.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeOverTimeResponse {
    private String id;
    private String employeeName;
    private int hours;
    private LocalDate date;
    private String description;
    private String approvedBy;
}
