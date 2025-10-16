package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AttendanceList
{
    private LocalDate date;
    private String attendanceStatus;
}
