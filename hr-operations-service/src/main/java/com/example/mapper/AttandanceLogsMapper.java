package com.example.mapper;

import com.example.dto.response.AttendanceLogsResponse;
import com.example.entity.AttendanceLogs;
import com.example.util.UtilsMethods;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttandanceLogsMapper {
    public static AttendanceLogs convertAttendenceLogRequestToAttandenceLog(String punchType) {
        return AttendanceLogs.builder()
                .id(UtilsMethods.generateId())
                .punchTime(LocalTime.now())
                .punchType(punchType)
                .date(LocalDate.now())
                .bmType("System")
                .location("Remote")
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public static AttendanceLogsResponse AttandenceLogToAttandenceLogResponse(AttendanceLogs attendanceLogs) {
        return AttendanceLogsResponse.builder()
                .userId(attendanceLogs.getUser())
                .punchTime(attendanceLogs.getPunchTime())
                .punchType(attendanceLogs.getPunchType())
                .date(attendanceLogs.getDate())
                .bmType(attendanceLogs.getBmType())
                .location(attendanceLogs.getLocation())
                .build();
    }
}
