package com.moonstack.dtos.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo
{
    private String userId;
    private String name;
    private String email;
    private String role;
}
