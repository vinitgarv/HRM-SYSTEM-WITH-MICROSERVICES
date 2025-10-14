package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.EmployeeOvertimeRequest;
import com.example.dto.response.EmployeeOverTimeResponse;
import com.example.service.EmployeeOvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/overtime")
public class EmployeeOvertimeController {

    @Autowired
    private EmployeeOvertimeService employeeOvertimeService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> createAttendanceApprovalRequest(@RequestBody List<EmployeeOvertimeRequest> employeeOvertimeRequests, @PathVariable String userId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeOvertimeService.createOvertimeRequest(employeeOvertimeRequests,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/approve/{overTimeId}")
    public ResponseEntity<ApiResponse<String>> approveOverTimeRequest(@PathVariable String overTimeId) {
        employeeOvertimeService.approveOvertimeRequest(overTimeId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("OvertTime Request  Approved Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reject/{overTimeId}")
    public ResponseEntity<ApiResponse<String>> rejectOverTimeRequest(@PathVariable String overTimeId) {
        employeeOvertimeService.rejectOvertimeRequest(overTimeId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("OvertTime Request  Rejected Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeOverTimeResponse>>> getAllEmplpoyeesOverTIme() {
        ApiResponse<List<EmployeeOverTimeResponse>> response = ApiResponse.<List<EmployeeOverTimeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.TRUE)
                .data(employeeOvertimeService.getAllEmployeesOverTIme())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{overTimeId}")
    public ResponseEntity<ApiResponse<EmployeeOverTimeResponse>> getLeaveById(@PathVariable String overTimeId) {
        ApiResponse<EmployeeOverTimeResponse> response = ApiResponse.<EmployeeOverTimeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeOvertimeService.getById(overTimeId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{overTimeId}")
    public ResponseEntity<ApiResponse<String>> deleteLeaveRequestById(@PathVariable String overTimeId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeOvertimeService.deleteOverTImeRequestById(overTimeId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{overTimeId}")
    public ResponseEntity<ApiResponse<EmployeeOverTimeResponse>> updateLeaveRequestById(@PathVariable String overTimeId, @RequestBody EmployeeOvertimeRequest employeeOvertimeRequestRequest) {
        ApiResponse<EmployeeOverTimeResponse> response = ApiResponse.<EmployeeOverTimeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeOvertimeService.updateOverTimeRequest(overTimeId,employeeOvertimeRequestRequest))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
