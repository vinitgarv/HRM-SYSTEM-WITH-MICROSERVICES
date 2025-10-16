package com.example.mapper;

import com.example.dto.request.EmployeeOvertimeRequest;
import com.example.entity.EmployeeOvertime;
import com.example.enums.RequestStatus;
import com.example.util.UtilsMethods;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeOvertimeMapper {
    public static List<EmployeeOvertime> createEmployeeOverTimeRequestToEmployeeOverTime(List<EmployeeOvertimeRequest> requests) {
        return requests.stream()
                .map(request -> EmployeeOvertime.builder()
                        .id(UtilsMethods.generateId())
                        .isActive(true)
                        .isDeleted(false)
                        .employeeName(request.getEmployeeName())
                        .date(request.getDate())
                        .hours(request.getHours())
                        .description(request.getDescription())
                        .status(RequestStatus.PENDING)
                        .build())
                .collect(Collectors.toList());
    }
}
