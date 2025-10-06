//package com.example.controller;
//
//import com.example.apiResponse.ApiResponse;
//import com.example.constants.Message;
//import com.example.dto.request.EmailRequest;
//import com.example.dto.request.HolidayCalendarRequest;
//import com.example.dto.response.HolidayCalendarResponse;
//import com.example.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/email")
//public class EmailController
//{
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse<String>> add(@RequestBody EmailRequest emailRequest) {
//        ApiResponse<String> response = ApiResponse.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(Message.SUCCESS)
//                .multiple(Message.FALSE)
//                .data(emailService.sendEmail(emailRequest))
//                .build();
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//}
