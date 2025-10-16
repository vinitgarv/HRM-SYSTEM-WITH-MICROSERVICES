package com.moonstack.controller;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.MessageRequest;
import com.moonstack.dtos.response.MessageResponse;
import com.moonstack.response.ApiResponse;
import com.moonstack.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController
{
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<MessageResponse>>> register(@RequestBody List<MessageRequest> requests)
    {
        ApiResponse<List<MessageResponse>> response = ApiResponse.<List<MessageResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(messageService.addMsg(requests))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/filter/{value}")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> filter(@PathVariable("value") String value)
    {
        ApiResponse<List<MessageResponse>> response = ApiResponse.<List<MessageResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data(messageService.filter(value))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh()
    {
        messageService.refreshMessages();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.SUCCESS)
                .multiple(false)
                .data("Message refreshed successfully")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
