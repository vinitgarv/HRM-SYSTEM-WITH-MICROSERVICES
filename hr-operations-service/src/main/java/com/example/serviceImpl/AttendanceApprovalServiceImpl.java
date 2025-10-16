package com.example.serviceImpl;

import com.example.client.UserClient;
import com.example.dto.request.AttendanceApprovalRequest;
import com.example.entity.AttendanceApproval;
import com.example.entity.AttendanceData;
import com.example.enums.RequestStatus;
import com.example.repository.AttendanceApprovalRepository;
import com.example.service.AttendanceApprovalService;
import com.example.service.AttendanceDataService;
import com.example.util.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceApprovalServiceImpl implements AttendanceApprovalService {

    @Autowired
    private  AttendanceApprovalRepository attendanceApprovalRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private AttendanceDataService attendanceDataService;

    @Override
    public AttendanceApproval createRequest(AttendanceApprovalRequest attendanceApprovalRequest) {
        AttendanceApproval approval = AttendanceApproval.builder()
               .id(UtilsMethods.generateId())
               .user(attendanceApprovalRequest.getUserId())
               .reason(attendanceApprovalRequest.getReason())
               .requestedInTime(attendanceApprovalRequest.getInTime())
               .requestedOutTime(attendanceApprovalRequest.getOutTime())
               .status(RequestStatus.PENDING)
               .requestDate(attendanceApprovalRequest.getRequestDate())
               .isActive(true)
               .isDeleted(false)
               .build();
        attendanceApprovalRepository.save(approval);
        return  approval;
    }

    @Override
    @Transactional
    public AttendanceApproval approveRequest(String requestId) {
        AttendanceApproval request = attendanceApprovalRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.APPROVED);
        attendanceApprovalRepository.save(request);

        AttendanceData data = attendanceDataService.findByUserAndDate(request.getUser(), request.getRequestDate());
        data.setAttendanceStatus("present");
        data.setValid(true);

        if (request.getRequestedInTime() != null) {
            data.setFirstPunchInTime(request.getRequestedInTime());
        }
        if (request.getRequestedOutTime() != null) {
            data.setLastPunchoutTime(request.getRequestedOutTime());
        }

        attendanceDataService.add(data);
        return request;
    }

    @Override
    public AttendanceApproval rejectRequest(String requestId) {
        AttendanceApproval request = attendanceApprovalRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.REJECTED);
         attendanceApprovalRepository.save(request);
         return  request;
    }
}
