package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.AttandenceLogsRequest;
import com.example.dto.request.AttendanceRecord;
import com.example.dto.response.AttendanceDataResponse;
import com.example.dto.response.AttendanceLogsResponse;
import com.example.service.AttendanceLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceLogsController {
    @Autowired
    private AttendanceLogsService attendanceLogsService;

    @PostMapping("/punch-in/{userId}")
    public ResponseEntity<ApiResponse<String>> punchIn(@PathVariable String userId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(attendanceLogsService.punchIn(userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceRecord>>> getAllUser()
    {
        ApiResponse<List<AttendanceRecord>> response = ApiResponse.<List<AttendanceRecord>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(attendanceLogsService.getAllAttendance())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/add-user-attendance")
    public ResponseEntity<ApiResponse<List<AttendanceLogsResponse>>> addAllUserAttendance()
    {
        ApiResponse<List<AttendanceLogsResponse>> response = ApiResponse.<List<AttendanceLogsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(attendanceLogsService.addAllUserAttendance())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{date}/{userId}")
    public ResponseEntity<ApiResponse<List<AttendanceLogsResponse>>> getAllAttendanceOfaUser(@PathVariable("date") LocalDate date,@PathVariable("userId") String userId)
    {
        ApiResponse<List<AttendanceLogsResponse>> response = ApiResponse.<List<AttendanceLogsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(attendanceLogsService.getAllAttendanceOfaUserByDate(date,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{userId}/{month}/{year}")
    public ResponseEntity<ApiResponse<List<AttendanceLogsResponse>>> getMonthlyUserAttendance(@PathVariable("userId") String userId,@PathVariable("month") Integer month,@PathVariable("year") Integer year)
    {
        ApiResponse<List<AttendanceLogsResponse>> response = ApiResponse.<List<AttendanceLogsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(attendanceLogsService.getAllAttendanceOfaUserByMonth(userId,month,year))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/info/{userId}/{month}/{year}")
    public ResponseEntity<ApiResponse<AttendanceDataResponse>> userAttendanceWithData(@PathVariable("userId") String userId, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        ApiResponse<AttendanceDataResponse> response = ApiResponse.<AttendanceDataResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(attendanceLogsService.userAttendance(userId,month,year))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
