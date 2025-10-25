package com.moonstack.controller;

import com.moonstack.apiResponse.ApiResponse;
import com.moonstack.constant.Message;
import com.moonstack.dto.request.EmployeeSalaryRequest;
import com.moonstack.dto.response.EmployeeSalaryResponse;
import com.moonstack.service.EmployeeSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp-salary")
public class EmployeeSalaryController
{
    @Autowired
    private EmployeeSalaryService service;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<EmployeeSalaryResponse>> add(@RequestBody EmployeeSalaryRequest request, @PathVariable String userId) {
        ApiResponse<EmployeeSalaryResponse> response = ApiResponse.<EmployeeSalaryResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.add(request,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{salaryId}")
    public ResponseEntity<ApiResponse<EmployeeSalaryResponse>> update(@RequestBody EmployeeSalaryRequest request, @PathVariable String salaryId) {
        ApiResponse<EmployeeSalaryResponse> response = ApiResponse.<EmployeeSalaryResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.update(request,salaryId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<EmployeeSalaryResponse>> getById(@PathVariable String userId)
    {
        ApiResponse<EmployeeSalaryResponse> response = ApiResponse.<EmployeeSalaryResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.getById(userId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
