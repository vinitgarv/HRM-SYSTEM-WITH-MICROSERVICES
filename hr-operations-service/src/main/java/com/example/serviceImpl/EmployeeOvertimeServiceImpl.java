package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.EmployeeOvertimeRequest;
import com.example.dto.response.EmployeeOverTimeResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.EmployeeOvertime;
import com.example.enums.RequestStatus;
import com.example.mapper.EmployeeOvertimeMapper;
import com.example.repository.EmployeeOvertimeRepository;
import com.example.service.EmployeeOvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeOvertimeServiceImpl  implements EmployeeOvertimeService {
    @Autowired
    private EmployeeOvertimeRepository repository;

    @Autowired
    private UserClient userClient;

    @Override
    public String createOvertimeRequest(List<EmployeeOvertimeRequest> employeeOvertimeRequests, String userId) {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        List<EmployeeOvertime> employeeOvertimeList = EmployeeOvertimeMapper.createEmployeeOverTimeRequestToEmployeeOverTime(employeeOvertimeRequests);

        for (EmployeeOvertime overtime : employeeOvertimeList) {
            Optional<EmployeeOvertime> existing = repository.findByUserAndDate(userId, overtime.getDate());

            if (!existing.isEmpty()) {
                throw new IllegalStateException("OverTime request already exists for given dates");
            }

            overtime.setUser(userId);
        }

        repository.saveAll(employeeOvertimeList);
        return "Overtime Request Created Succesfully";
    }

    @Override
    public EmployeeOvertime approveOvertimeRequest(String overTimeRequestId) {
        EmployeeOvertime request = repository.findById(overTimeRequestId)
                .orElseThrow(() -> new RuntimeException("OverTime Request not found"));
        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedBy("Vinit Garg");
        repository.save(request);
        return request;
    }

    @Override
    public EmployeeOvertime rejectOvertimeRequest(String overTimeRequestId) {
        EmployeeOvertime request = repository.findById(overTimeRequestId)
                .orElseThrow(() -> new RuntimeException("OverTime Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        request.setApprovedBy("Vinit Garg");
        repository.save(request);
        return request;
    }

    @Override
    public List<EmployeeOverTimeResponse> getAllEmployeesOverTIme() {
        List<EmployeeOvertime> employeeOverTimeList = repository.findAll();

        List<EmployeeOverTimeResponse> overTimeResponses = employeeOverTimeList.stream()
                .map(overtime -> EmployeeOverTimeResponse.builder()
                        .id(overtime.getId())
                        .date(overtime.getDate())
                        .employeeName(overtime.getEmployeeName())
                        .hours(overtime.getHours())
                        .approvedBy(overtime.getApprovedBy())
                        .description(overtime.getDescription())
                        .build())
                .toList();
        return overTimeResponses;
    }

    @Override
    public EmployeeOverTimeResponse getById(String overTimeRequestId) {
        EmployeeOvertime request = repository.findById(overTimeRequestId)
                .orElseThrow(() -> new RuntimeException("OverTime Request not found"));

        return EmployeeOverTimeResponse.builder()
                .id(request.getId())
                .employeeName(request.getEmployeeName())
                .hours(request.getHours())
                .date(request.getDate())
                .approvedBy(request.getApprovedBy())
                .description(request.getDescription())
                .build();
    }

    @Override
    public String deleteOverTImeRequestById(String overTimeRequestId) {
        EmployeeOvertime request = repository.findById(overTimeRequestId)
                .orElseThrow(() -> new RuntimeException("OverTime Request not found"));
        repository.deleteById(overTimeRequestId);
        return "OverTImeRequest Deleted SuccessFully";
    }

    @Override
    public EmployeeOverTimeResponse updateOverTimeRequest(String overTimeRequestId, EmployeeOvertimeRequest updatedRequest) {
        EmployeeOvertime request = repository.findById(overTimeRequestId)
                .orElseThrow(() -> new RuntimeException("OverTime Request not found"));

        request.setEmployeeName(updatedRequest.getEmployeeName());
        request.setDate(updatedRequest.getDate());
        request.setHours(updatedRequest.getHours());
        request.setDescription(updatedRequest.getDescription());

       EmployeeOvertime updated = repository.save(request);

        return EmployeeOverTimeResponse.builder()
                .id(updated.getId())
                .employeeName(updated.getEmployeeName())
                .date(updated.getDate())
                .hours(updated.getHours())
                .approvedBy(updated.getApprovedBy())
                .description(updated.getDescription())
                .build();
    }
}
