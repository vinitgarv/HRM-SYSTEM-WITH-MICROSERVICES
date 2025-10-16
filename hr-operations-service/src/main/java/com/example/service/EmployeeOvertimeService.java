package com.example.service;

import com.example.dto.request.EmployeeOvertimeRequest;
import com.example.dto.response.EmployeeOverTimeResponse;
import com.example.entity.EmployeeOvertime;
import java.util.List;

public interface EmployeeOvertimeService {
    String createOvertimeRequest(List<EmployeeOvertimeRequest> employeeOvertimeRequests, String userId);

    EmployeeOvertime approveOvertimeRequest(String overTimeRequestId);

    EmployeeOvertime rejectOvertimeRequest(String overTimeRequestId);

    List<EmployeeOverTimeResponse>  getAllEmployeesOverTIme();

    EmployeeOverTimeResponse getById(String overTimeRequestId);

    String deleteOverTImeRequestById(String overTimeRequestId);

    EmployeeOverTimeResponse updateOverTimeRequest(String overTimeRequestId, EmployeeOvertimeRequest updatedRequest);
}
