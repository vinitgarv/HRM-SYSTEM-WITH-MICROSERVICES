package com.example.service;

import com.example.entity.AttendanceData;

import java.time.LocalDate;

public interface AttendanceDataService
{
    AttendanceData add(AttendanceData attendanceData);

    AttendanceData findByUserAndDate(String user, LocalDate date);

    void checkLateEmployees();
}

