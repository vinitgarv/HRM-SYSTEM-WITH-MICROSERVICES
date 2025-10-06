package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.dto.response.PageResponse;
import com.example.service.HolidayCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holiday")
public class HolidayCalendarController
{
    @Autowired
    private HolidayCalendarService service;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<HolidayCalendarResponse>> add(@RequestBody HolidayCalendarRequest request) {
        ApiResponse<HolidayCalendarResponse> response = ApiResponse.<HolidayCalendarResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.add(request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<HolidayCalendarResponse>> add(@PathVariable("id") String id) {
        ApiResponse<HolidayCalendarResponse> response = ApiResponse.<HolidayCalendarResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.getById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllHolidays")
    public ResponseEntity<ApiResponse<PageResponse<HolidayCalendarResponse>>> getAllHolidays(@RequestParam(defaultValue = "0") Integer page,
                                                                                             @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<HolidayCalendarResponse>> response = ApiResponse.<PageResponse<HolidayCalendarResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(service.getAllHolidays(page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ApiResponse<HolidayCalendarResponse> update(@PathVariable("id") String id, @RequestBody HolidayCalendarRequest request) {
        ApiResponse<HolidayCalendarResponse> response = ApiResponse.<HolidayCalendarResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(service.update(id,request))
                .build();
        return response;
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable("id") String id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(service.delete(id))
                .build();
        return response;
    }
}
