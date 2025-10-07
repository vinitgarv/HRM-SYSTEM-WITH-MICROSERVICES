package com.example.service;

import com.example.dto.request.AttendanceRecord;
import com.example.dto.response.AttendanceDataResponse;
import com.example.dto.response.AttendanceLogsResponse;


import java.time.LocalDate;
import java.util.List;

public interface AttendanceLogsService {
   String punchIn(String userId);

   List<AttendanceLogsResponse> addAllUserAttendance();

   List<AttendanceRecord> getAllAttendance();

   List<AttendanceLogsResponse> getAllLogsOfaUser(String id);

   List<AttendanceLogsResponse> getAllAttendanceOfaUserByDate(LocalDate date,String userId);

   List<AttendanceLogsResponse> getAllAttendanceOfaUserByMonth(String userId,int month, int year);

   AttendanceDataResponse userAttendance(String userId,int month , int year);
}
