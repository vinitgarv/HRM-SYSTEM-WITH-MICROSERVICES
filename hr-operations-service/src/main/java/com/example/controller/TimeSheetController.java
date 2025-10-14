package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.request.TimeSheetRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.dto.response.PageResponse;
import com.example.dto.response.TimeSheetResponse;
import com.example.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/time-sheet")
public class TimeSheetController
{
    @Autowired
    private TimeSheetService timeSheetService;

    @PostMapping
    public ResponseEntity<ApiResponse<TimeSheetResponse>> add(@RequestBody TimeSheetRequest request) {
        ApiResponse<TimeSheetResponse> response = ApiResponse.<TimeSheetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(timeSheetService.add(request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TimeSheetResponse>> getById(@PathVariable("id") String id) {
        ApiResponse<TimeSheetResponse> response = ApiResponse.<TimeSheetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(timeSheetService.getById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TimeSheetResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                                       @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<TimeSheetResponse>> response = ApiResponse.<PageResponse<TimeSheetResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(timeSheetService.getAll(page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<TimeSheetResponse> update(@PathVariable("id") String id, @RequestBody TimeSheetRequest request) {
        ApiResponse<TimeSheetResponse> response = ApiResponse.<TimeSheetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(timeSheetService.update(id,request))
                .build();
        return response;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable("id") String id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(timeSheetService.delete(id))
                .build();
        return response;
    }

    @PutMapping("/approve/{timeSheetId}/{approverId}")
    public ApiResponse<TimeSheetResponse> approved(@PathVariable("timeSheetId") String timeSheetId, @PathVariable("approverId")  String approverId) {
        ApiResponse<TimeSheetResponse> response = ApiResponse.<TimeSheetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(timeSheetService.timeSheetApproved(timeSheetId,approverId))
                .build();
        return response;
    }

    @PutMapping("/reject/{timeSheetId}/{approverId}")
    public ApiResponse<TimeSheetResponse> rejected(@PathVariable("timeSheetId") String timeSheetId, @PathVariable("approverId")  String approverId) {
        ApiResponse<TimeSheetResponse> response = ApiResponse.<TimeSheetResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(timeSheetService.timeSheetRejected(timeSheetId,approverId))
                .build();
        return response;
    }

    @GetMapping("/filter/{status}")
    public ResponseEntity<ApiResponse<PageResponse<TimeSheetResponse>>> getAll(@PathVariable("status") String status,@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<TimeSheetResponse>> response = ApiResponse.<PageResponse<TimeSheetResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(timeSheetService.filterByStatus(status,page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
