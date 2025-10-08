package in.hrm.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>  {
    private Integer statusCode;
    private String message;
    private Boolean multiple;
    private T data;
}