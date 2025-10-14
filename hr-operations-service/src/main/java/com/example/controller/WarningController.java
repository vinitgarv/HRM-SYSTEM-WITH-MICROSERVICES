package com.example.controller;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import com.example.dto.request.HolidayCalendarRequest;
import com.example.dto.request.WarningRequest;
import com.example.dto.response.HolidayCalendarResponse;
import com.example.dto.response.PageResponse;
import com.example.dto.response.WarningResponse;
import com.example.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warning")
public class WarningController
{
    @Autowired
    private WarningService warningService;

    @PostMapping
    public ResponseEntity<ApiResponse<WarningResponse>> add(@RequestBody WarningRequest request) {
        ApiResponse<WarningResponse> response = ApiResponse.<WarningResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(warningService.addWarning(request))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WarningResponse>> add(@PathVariable("id") String id) {
        ApiResponse<WarningResponse> response = ApiResponse.<WarningResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(warningService.getById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<WarningResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                             @RequestParam(defaultValue = "10") Integer size) {
        ApiResponse<PageResponse<WarningResponse>> response = ApiResponse.<PageResponse<WarningResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(Message.FALSE)
                .data(warningService.getAllWarnings(page,size))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<WarningResponse> update(@PathVariable("id") String id, @RequestBody WarningRequest request) {
        ApiResponse<WarningResponse> response = ApiResponse.<WarningResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(warningService.update(id,request))
                .build();
        return response;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable("id") String id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(true)
                .data(warningService.delete(id))
                .build();
        return response;
    }
}
