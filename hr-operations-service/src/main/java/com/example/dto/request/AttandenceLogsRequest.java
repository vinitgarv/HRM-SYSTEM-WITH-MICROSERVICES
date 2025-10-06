package com.example.dto.request;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttandenceLogsRequest {
    private String punchType;
    private LocalTime punchTime;
    private LocalDate date;
    private String bmType;
    private String location;
}
