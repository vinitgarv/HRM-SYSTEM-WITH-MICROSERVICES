package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.AttendanceApprovalRequest;
import com.example.service.AttendanceApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance-approval")
public class AttendanceApprovalController {

    @Autowired
    private AttendanceApprovalService attendanceApprovalService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createAttendanceApprovalRequest(@RequestBody AttendanceApprovalRequest attendanceApprovalRequest){
        attendanceApprovalService.createRequest(attendanceApprovalRequest);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Attendance Approval Request Created Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<ApiResponse<String>> approveRequest(@PathVariable String requestId) {
        attendanceApprovalService.approveRequest(requestId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Attendance Approval Approved Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<ApiResponse<String>> rejectRequest(@PathVariable String requestId) {
        attendanceApprovalService.rejectRequest(requestId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Attendance Approval Rejected Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    }
