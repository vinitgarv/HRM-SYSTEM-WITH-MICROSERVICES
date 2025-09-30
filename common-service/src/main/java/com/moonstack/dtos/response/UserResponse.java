package com.moonstack.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private Set<RoleResponse> roles;
}

