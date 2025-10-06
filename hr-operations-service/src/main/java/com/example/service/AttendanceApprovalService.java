package com.example.service;

import com.example.dto.request.AttendanceApprovalRequest;
import com.example.entity.AttendanceApproval;


public interface AttendanceApprovalService {

    AttendanceApproval createRequest(AttendanceApprovalRequest attendanceApprovalRequest);

    AttendanceApproval approveRequest(String requestId);
//
    AttendanceApproval rejectRequest(String requestId);
//
//    List<AttendanceApproval> getPendingRequests();
}
