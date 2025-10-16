package com.moonstack.apiResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private Integer statusCode;
    private String message;
    private Boolean multiple;
    private T data;
}
