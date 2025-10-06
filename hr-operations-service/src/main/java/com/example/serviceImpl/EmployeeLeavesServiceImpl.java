package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.EmployeeLeavesRequest;
import com.example.dto.response.EmployeeLeavesResponse;
import com.example.dto.response.LeaveResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.EmployeeLeaves;
import com.example.enums.RequestStatus;
import com.example.mapper.EmployeeLeavesMapper;
import com.example.repository.EmployeeLeavesRepository;
import com.example.service.EmployeeLeavesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeLeavesServiceImpl implements EmployeeLeavesService {

    @Autowired
    private EmployeeLeavesRepository repository;

    @Autowired
    private UserClient userClient;

    @Override
    public String createLeaveRequest(List<EmployeeLeavesRequest> employeeLeavesRequest, String userId) {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        List<EmployeeLeaves> employeeLeavesList = EmployeeLeavesMapper
                .createEmployeeLeavesRequestToEmployeeLeaves(employeeLeavesRequest);

        for (EmployeeLeaves leave : employeeLeavesList) {
            List<EmployeeLeaves> existing = repository.findOverlappingLeaves(userId, leave.getStartDate(), leave.getEndDate());

            if (!existing.isEmpty()) {
                throw new IllegalStateException("Leave request already exists for given dates: "
                        + leave.getStartDate() + " to " + leave.getEndDate());
            }

            leave.setUser(userId);
        }
        repository.saveAll(employeeLeavesList);
        return "Leave Requests Created Successfully";
    }

    @Override
    public EmployeeLeavesResponse getAllEmployeeLeaves() {
        List<EmployeeLeaves> employeeLeaveList = repository.findAll();

        List<LeaveResponse> leaveResponses = employeeLeaveList.stream().map(leave -> LeaveResponse.builder()
                .id(leave.getId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .days(leave.getDays())
                .reason(leave.getReason())
                .status(String.valueOf(leave.getStatus()))
                .build()).toList();

        int totalLeave = leaveResponses.size();
        int approvedLeaves = (int) leaveResponses.stream()
                .filter(l -> "APPROVED".equalsIgnoreCase(l.getStatus()))
                .count();
        int rejectedLeaves = (int) leaveResponses.stream()
                .filter(l -> "REJECTED".equalsIgnoreCase(l.getStatus()))
                .count();
        int pendingLeaves = (int) leaveResponses.stream()
                .filter(l -> "PENDING".equalsIgnoreCase(l.getStatus()))
                .count();

        return EmployeeLeavesResponse.builder()
                .leaveResponses(leaveResponses)
                .totalLeave(totalLeave)
                .approvedLeaves(approvedLeaves)
                .rejectedLeaves(rejectedLeaves)
                .pendingLeaves(pendingLeaves)
                .build();
    }

    @Override
    public EmployeeLeaves approveLeaveRequest(String leaveId) {
        EmployeeLeaves request = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));
        request.setStatus(RequestStatus.APPROVED);
        repository.save(request);
        return  request;
    }

    @Override
    public EmployeeLeaves rejectLeaveRequest(String leaveId) {
        EmployeeLeaves request = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        repository.save(request);
        return  request;
    }

    @Override
    public LeaveResponse getById(String leaveId) {
        EmployeeLeaves request = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        return LeaveResponse.builder()
                .id(request.getId())
                .leaveType(request.getLeaveType())
                .status(String.valueOf(request.getStatus()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .days(request.getDays())
                .reason(request.getReason())
                .build();
    }

    @Override
    public String deleteLeaveRequestById(String leaveId) {
        EmployeeLeaves request = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));
         repository.deleteById(leaveId);
        return "Leave Request Deleted Successfully";
    }

    @Override
    public LeaveResponse updateLeaveRequest(String leaveId, EmployeeLeavesRequest updatedRequest) {
        EmployeeLeaves request = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        List<EmployeeLeaves> overlappingLeaves = repository.findOverlappingLeaves(
                request.getUser(),
                updatedRequest.getStartDate(),
                updatedRequest.getEndDate()
        );

        overlappingLeaves = overlappingLeaves.stream()
                .filter(l -> !l.getId().equals(leaveId))
                .toList();

        if (!overlappingLeaves.isEmpty()) {
            throw new IllegalStateException("Leave request already exists for given dates: "
                    + updatedRequest.getStartDate() + " to " + updatedRequest.getEndDate());
        }
        request.setLeaveType(updatedRequest.getLeaveType());
        request.setReason(updatedRequest.getReason());
        request.setStartDate(updatedRequest.getStartDate());
        request.setEndDate(updatedRequest.getEndDate());
        request.setDays((int) (updatedRequest.getEndDate().toEpochDay() - updatedRequest.getStartDate().toEpochDay() + 1));

        EmployeeLeaves updated = repository.save(request);

        return LeaveResponse.builder()
                .id(updated.getId())
                .leaveType(updated.getLeaveType())
                .status(String.valueOf(request.getStatus()))
                .startDate(updated.getStartDate())
                .endDate(updated.getEndDate())
                .days(updated.getDays())
                .reason(updated.getReason())
                .build();
    }
}
