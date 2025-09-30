package com.moonstack.mapper;

import com.moonstack.dtos.request.HierarchyInfoRequest;
import com.moonstack.dtos.response.HierarchyInfoResponse;
import com.moonstack.entity.HierarchyInfo;
import com.moonstack.utils.Helper;

public class HierarchyInfoMapper
{
    public static HierarchyInfo hierarchyInfoRequestIntoHierarchyInfo(HierarchyInfoRequest request)
    {
        return HierarchyInfo.builder()
                .id(Helper.generateId())
                .reportingManager(request.getReportingManager())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static HierarchyInfoResponse hierarchyInfoIntoHierarchyInfoResponse(HierarchyInfo hierarchyInfo)
    {
        return HierarchyInfoResponse.builder()
                .reportingManager(hierarchyInfo.getReportingManager())
                .build();
    }
}
