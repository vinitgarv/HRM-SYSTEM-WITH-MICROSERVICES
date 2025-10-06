package com.example.service;

import com.example.dto.request.AttendanceRecord;
import com.example.dto.response.AttendanceDataResponse;
import com.example.dto.response.AttendanceLogsResponse;


import java.time.LocalDate;
import java.util.List;

public interface AttendanceLogsService {
    String punchIn(String userId);

   //AttendanceRecord getAttendanceByUserId(String userId);

  // void add(AttandenceLogsRequest request);

   List<AttendanceLogsResponse> addAllUserAttendance();

   public List<AttendanceRecord> getAllAttendance();

   List<AttendanceLogsResponse> getAllLogsOfaUser(String id);

   List<AttendanceLogsResponse> getAllAttendanceOfaUserByDate(LocalDate date,String userId);
   List<AttendanceLogsResponse> getAllAttendanceOfaUserByMonth(String userId,int month, int year);

   AttendanceDataResponse userAttendance(String userId,int month , int year);
}
