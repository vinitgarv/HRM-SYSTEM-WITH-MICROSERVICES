package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.EmployeeLeavesRequest;
import com.example.dto.response.EmployeeLeavesResponse;
import com.example.dto.response.LeaveResponse;
import com.example.service.EmployeeLeavesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-leave")
public class EmployeeLeavesController {
    @Autowired
    private EmployeeLeavesService employeeLeavesService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> createAttendanceApprovalRequest(@RequestBody List<EmployeeLeavesRequest> employeeLeavesRequest, @PathVariable String userId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeLeavesService.createLeaveRequest(employeeLeavesRequest,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<EmployeeLeavesResponse>> getAllEmplpoyeesLeaves() {
        ApiResponse<EmployeeLeavesResponse> response = ApiResponse.<EmployeeLeavesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeLeavesService.getAllEmployeeLeaves())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/approve/{leaveId}")
    public ResponseEntity<ApiResponse<String>> approveLeaveRequest(@PathVariable String leaveId) {
        employeeLeavesService.approveLeaveRequest(leaveId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Leave Approval Approved Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/reject/{leaveId}")
    public ResponseEntity<ApiResponse<String>> rejectLeaveRequest(@PathVariable String leaveId) {
        employeeLeavesService.rejectLeaveRequest(leaveId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data("Attendance Approval Rejected Successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{leaveId}")
    public ResponseEntity<ApiResponse<LeaveResponse>> getLeaveById(@PathVariable String leaveId) {
        ApiResponse<LeaveResponse> response = ApiResponse.<LeaveResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeLeavesService.getById(leaveId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{leaveId}")
    public ResponseEntity<ApiResponse<String>> deleteLeaveRequestById(@PathVariable String leaveId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeLeavesService.deleteLeaveRequestById(leaveId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{leaveId}")
    public ResponseEntity<ApiResponse<LeaveResponse>> updateLeaveRequestById(@PathVariable String leaveId, @RequestBody EmployeeLeavesRequest employeeLeavesRequest) {
        ApiResponse<LeaveResponse> response = ApiResponse.<LeaveResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(employeeLeavesService.updateLeaveRequest(leaveId,employeeLeavesRequest))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
