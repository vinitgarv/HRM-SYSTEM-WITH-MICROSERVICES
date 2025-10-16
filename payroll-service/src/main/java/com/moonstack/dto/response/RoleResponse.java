package com.moonstack.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleResponse {
    private String id;
    private String name;
}