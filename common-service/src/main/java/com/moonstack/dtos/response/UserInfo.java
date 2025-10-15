package com.moonstack.dtos.response;

import lombok.*;

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
}
