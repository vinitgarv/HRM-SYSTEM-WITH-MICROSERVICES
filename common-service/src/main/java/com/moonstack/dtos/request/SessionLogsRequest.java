package com.moonstack.dtos.request;


import com.moonstack.constants.Message;
import com.moonstack.entity.User;
import com.moonstack.exception.NotFoundException;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionLogsRequest {
    private String reason;
    private String action;
    private User user;
    private String ipAddress;
}
