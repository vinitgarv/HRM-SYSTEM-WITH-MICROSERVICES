package com.example.exception;

import com.example.apiResponse.ApiResponse;
import com.example.constants.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestFailedException.class)
    public ResponseEntity<ApiResponse<Object>> handleRequestFailedException(RequestFailedException e)
    {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(Message.FAILURE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthorizationException(AuthenticationException e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data("Login Failed")
                .build();
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidException(InvalidException e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyPresentException(AlreadyPresentException e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Message.FAILURE)
                .multiple(Message.FALSE)
                .data(e.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
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
}
