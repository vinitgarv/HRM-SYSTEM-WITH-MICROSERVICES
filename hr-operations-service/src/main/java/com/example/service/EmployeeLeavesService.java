package com.example.service;

import com.example.dto.request.EmployeeLeavesRequest;
import com.example.dto.response.EmployeeLeavesResponse;
import com.example.dto.response.LeaveResponse;
import com.example.entity.EmployeeLeaves;

import java.util.List;

public interface EmployeeLeavesService {
    String createLeaveRequest(List<EmployeeLeavesRequest> employeeLeavesRequest,String userId);
    EmployeeLeavesResponse getAllEmployeeLeaves();
    EmployeeLeaves approveLeaveRequest(String leaveId);
    EmployeeLeaves rejectLeaveRequest(String leaveId);
    LeaveResponse getById(String leaveId);
    String deleteLeaveRequestById(String leaveId);
    LeaveResponse updateLeaveRequest(String leaveId,EmployeeLeavesRequest updatedRequest);
}
