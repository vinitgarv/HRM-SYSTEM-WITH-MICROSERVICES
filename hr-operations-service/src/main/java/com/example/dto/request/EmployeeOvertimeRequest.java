package com.example.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeOvertimeRequest {
    private String employeeName;
    private LocalDate date;
    private int hours;
    private String description;
}
