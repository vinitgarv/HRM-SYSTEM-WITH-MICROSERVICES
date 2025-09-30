package com.moonstack.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>
{
    private Integer statusCode;
    private String message;
    private Boolean multiple;
    private T data;
}
