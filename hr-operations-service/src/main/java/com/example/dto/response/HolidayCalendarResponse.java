package com.example.dto.response;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayCalendarResponse {

    private String id;
    private LocalDate date;
    private String day;
    private String remark;
    private String type;
}
