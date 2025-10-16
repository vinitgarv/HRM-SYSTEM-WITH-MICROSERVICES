package com.example.mapper;

import com.example.dto.request.EmployeeLeavesRequest;
import com.example.entity.EmployeeLeaves;
import com.example.enums.RequestStatus;
import com.example.util.UtilsMethods;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeLeavesMapper {
    public static List<EmployeeLeaves> createEmployeeLeavesRequestToEmployeeLeaves(List<EmployeeLeavesRequest> requests) {
        return requests.stream()
                .map(request -> EmployeeLeaves.builder()
                        .id(UtilsMethods.generateId())
                        .isActive(true)
                        .isDeleted(false)
                        .leaveType(request.getLeaveType())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .reason(request.getReason())
                        .days((int) (request.getEndDate().toEpochDay() - request.getStartDate().toEpochDay() + 1))
                        .status(RequestStatus.PENDING)
                        .build())
                .collect(Collectors.toList());
    }

}
