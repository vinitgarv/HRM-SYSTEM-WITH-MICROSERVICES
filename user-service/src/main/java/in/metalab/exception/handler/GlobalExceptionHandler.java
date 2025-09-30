package in.metalab.exception.handler;

import in.metalab.constants.UserCommonMessages;
import in.metalab.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle custom business exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleCustomException(RuntimeException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        log.error("Problem occurred : {}", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.FAILED,HttpStatus.BAD_REQUEST.name(), String.valueOf(HttpStatus.BAD_REQUEST.value()), errorDetails), HttpStatus.BAD_REQUEST);

    }

    // Handle validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("errors", validationErrors);
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        log.error("Problem occurred : {}", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.FAILED,HttpStatus.BAD_REQUEST.name(), String.valueOf(HttpStatus.BAD_REQUEST.value()), errorDetails), HttpStatus.BAD_REQUEST);

    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error("Problem occurred : {}", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(UserCommonMessages.FAILED,HttpStatus.INTERNAL_SERVER_ERROR.name(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
