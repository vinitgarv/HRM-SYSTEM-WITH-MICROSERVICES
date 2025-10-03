package com.moonstack.mapper;

import com.moonstack.dtos.request.IdentityInfoRequest;
import com.moonstack.dtos.response.IdentityInfoResponse;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.utils.Helper;

public class IdentityInfoMapper
{
    public static IdentityInfo identityInfoRequestIntoIdentityInfo(IdentityInfoRequest request)
    {
        return IdentityInfo.builder()
                .id(Helper.generateId())
                .uan(request.getUan())
                .pan(request.getPan())
                .aadhar(request.getAadhar())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static IdentityInfoResponse identityInfoIntoIdentityInfoResponse(IdentityInfo identityInfo)
    {
        return IdentityInfoResponse.builder()
                .uan(identityInfo.getUan())
                .pan(identityInfo.getPan())
                .aadhar(identityInfo.getAadhar())
                .build();
    }
}
