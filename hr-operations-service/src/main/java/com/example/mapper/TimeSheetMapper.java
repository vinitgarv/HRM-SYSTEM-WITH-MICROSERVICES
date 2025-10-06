package com.example.mapper;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.TimeSheetRequest;
import com.example.dto.response.TimeSheetResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.TimeSheet;
import com.example.util.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeSheetMapper
{
    @Autowired
    private static UserClient userClient;

    public static TimeSheet convertTimeSheetRequestToTimeSheet(TimeSheetRequest request)
    {
        return TimeSheet.builder()
                .id(UtilsMethods.generateId())
                .datePosted(request.getDatePosted())
                .hours(request.getHours())
                .description(request.getDescription())
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public static TimeSheetResponse convertTimeSheetToTimeSheetResponse(TimeSheet timeSheet)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(timeSheet.getId());
        UserResponse user = apiResponse.getData();

        return TimeSheetResponse.builder()
                .id(timeSheet.getId())
                .employee(user.getFirstName()+" "+user.getLastName())
                .date(timeSheet.getDatePosted())
                .hours(timeSheet.getHours())
                .description(timeSheet.getDescription())
                .approvedBy(timeSheet.getApprovedBy())
                .approvalDate(timeSheet.getApprovalDate())
                .status(timeSheet.getStatus())
                .build();
    }
}
