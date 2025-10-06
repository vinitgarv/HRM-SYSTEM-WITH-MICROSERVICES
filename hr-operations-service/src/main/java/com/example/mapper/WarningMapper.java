package com.example.mapper;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.WarningRequest;
import com.example.dto.response.UserResponse;
import com.example.dto.response.WarningResponse;
import com.example.entity.Warning;
import com.example.util.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;

public class WarningMapper
{
    @Autowired
    private static UserClient userClient;

    public static Warning convertWarningRequestToWarning(WarningRequest request)
    {
        return Warning.builder()
                .id(UtilsMethods.generateId())
                .subject(request.getSubject())
                .warningDate(request.getWarningDate())
                .description(request.getDescription())
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public static WarningResponse convertWarningToWarningResponse(Warning warning)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(warning.getId());
        UserResponse user = apiResponse.getData();


        return WarningResponse.builder()
                .id(warning.getId())
                .name(user.getFirstName()+" "+user.getLastName())
                .subject(warning.getSubject())
                .warningDate(warning.getWarningDate())
                .description(warning.getDescription())
                .build();
    }
}
