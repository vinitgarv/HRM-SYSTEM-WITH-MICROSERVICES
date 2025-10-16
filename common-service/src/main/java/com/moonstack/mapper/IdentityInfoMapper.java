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
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .status("Pending Verification")
                .fileName(request.getFileName())
                .fileType(request.getFileType())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static IdentityInfoResponse identityInfoIntoIdentityInfoResponse(IdentityInfo identityInfo)
    {
        return IdentityInfoResponse.builder()
                .id(identityInfo.getId())
                .documentNumber(identityInfo.getDocumentNumber())
                .documentType(identityInfo.getDocumentType())
                .status(identityInfo.getStatus())
                .fileName(identityInfo.getFileName())
                .fileType(identityInfo.getFileType())
                .remark(identityInfo.getRemarks())
                .build();
    }
}
