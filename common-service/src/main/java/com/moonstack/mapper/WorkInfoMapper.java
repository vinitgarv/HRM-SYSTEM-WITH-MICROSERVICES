package com.moonstack.mapper;

import com.moonstack.dtos.request.WorkInfoRequest;
import com.moonstack.dtos.response.WorkInfoResponse;
import com.moonstack.entity.WorkInfo;
import com.moonstack.utils.Helper;

public class WorkInfoMapper
{
    public static WorkInfo workInfoRequestIntoWorkInfo(WorkInfoRequest request)
    {
        return WorkInfo.builder()
                .id(Helper.generateId())
                .department(request.getDepartment())
                .role(request.getRole())
                .location(request.getLocation())
                .empType(request.getEmpType())
                .designation(request.getDesignation())
                .empStatus(request.getEmpStatus())
                .sourceOfHire(request.getSourceOfHire())
                .dateOfJoining(request.getDateOfJoining())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static WorkInfoResponse workInfoIntoWorkInfoResponse(WorkInfo workInfo)
    {
        return WorkInfoResponse.builder()
                .department(workInfo.getDepartment())
                .role(workInfo.getRole())
                .location(workInfo.getLocation())
                .empType(workInfo.getEmpType())
                .designation(workInfo.getDesignation())
                .empStatus(workInfo.getEmpStatus())
                .sourceOfHire(workInfo.getSourceOfHire())
                .dateOfJoining(workInfo.getDateOfJoining())
                .build();
    }
}
