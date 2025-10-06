package com.example.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AttendanceLogsResponse
{
    private String userId;
    private String punchType;
    private LocalTime punchTime;
    private LocalDate date;
    private String bmType;
    private String location;
}
