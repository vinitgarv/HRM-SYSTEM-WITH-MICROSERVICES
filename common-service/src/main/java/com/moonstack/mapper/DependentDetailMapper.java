package com.moonstack.mapper;

import com.moonstack.dtos.request.DependentDetailRequest;
import com.moonstack.dtos.request.IdentityInfoRequest;
import com.moonstack.dtos.response.DependentDetailResponse;
import com.moonstack.entity.DependentDetail;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.utils.Helper;

public class DependentDetailMapper
{
    public static DependentDetail dependentDetailRequestIntoDependentDetail(DependentDetailRequest request)
    {
        return DependentDetail.builder()
                .id(Helper.generateId())
                .name(request.getName())
                .relation(request.getRelation())
                .dob(request.getDob())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static void updateFromRequest(DependentDetailRequest request, DependentDetail dependentDetail) {
     dependentDetail.setName(request.getName());
        dependentDetail.setRelation(request.getRelation());
        dependentDetail.setDob(request.getDob());
    }

    public static DependentDetailResponse dependentDetailIntoDependentDetailResponse(DependentDetail dependentDetail)
    {
        return DependentDetailResponse.builder()
                .name(dependentDetail.getName())
                .relation(dependentDetail.getRelation())
                .dob(dependentDetail.getDob())
                .build();
    }
}
