package com.moonstack.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionLogsResponse
{
    private String id;
    private String username;
    private Integer remainingAttempt;
    private String status; // fail or success
    private String reason;
    private String action;// login or logout
}
