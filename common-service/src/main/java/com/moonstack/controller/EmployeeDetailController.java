package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.response.ApiResponse;
import com.moonstack.service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeedetails")
public class EmployeeDetailController {

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody EmployeeDetailsRequest request, @PathVariable String userId)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.addEmployeeDetails(request,userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> update(@RequestBody EmployeeDetailsRequest request, @PathVariable String userId)
    {
        ApiResponse<EmployeeDetailsResponse> response = ApiResponse.<EmployeeDetailsResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.update(userId,request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")

    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getById( @PathVariable String userId)
    {
        ApiResponse<EmployeeDetailsResponse> response = ApiResponse.<EmployeeDetailsResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(employeeDetailsService.getById(userId))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
