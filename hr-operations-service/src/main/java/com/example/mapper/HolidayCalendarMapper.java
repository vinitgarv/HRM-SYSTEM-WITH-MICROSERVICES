package com.example.mapper;

import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.entity.HolidayCalendar;
import com.example.util.UtilsMethods;

import java.time.LocalDate;

public class HolidayCalendarMapper
{
    public static HolidayCalendar holidayCalendarRequestToHolidayCalendar(HolidayCalendarRequest request)
    {
        return  HolidayCalendar.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .date(request.getDate())
                .day(request.getDate().getDayOfWeek().toString())
                .remark(request.getRemark())
                .type(request.getType())
                .build();
    }

    public static HolidayCalendarResponse holidayCalendarToHolidayCalendarResponse(HolidayCalendar holidayCalendar)
    {
        return  HolidayCalendarResponse.builder()
                .id(holidayCalendar.getId())
                .date(holidayCalendar.getDate())
                .day(holidayCalendar.getDay())
                .remark(holidayCalendar.getRemark())
                .type(holidayCalendar.getType())
                .build();
    }
}
