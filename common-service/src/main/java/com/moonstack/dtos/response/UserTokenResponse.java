package com.moonstack.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenResponse
{
    private String userId;
    private Integer tokenVersion;
    private String firstName;
    private String lastName;
    private String email;
}
