package com.moonstack.exception;

import com.moonstack.response.ApiResponse;
import com.moonstack.constants.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(RequestFailedException.class)
    public ResponseEntity<ApiResponse<Object>> handleRequestFailedException(RequestFailedException e)
    {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(Message.FAIL)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException e)
    {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .data(Message.FAIL)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyPresentException(AlreadyPresentException e)
    {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .data(Message.FAIL)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(InvalidSessionException e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e)
    {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(Message.FAIL)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
