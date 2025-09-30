package com.moonstack.mapper;

import com.moonstack.dtos.request.SystemFieldRequest;
import com.moonstack.dtos.response.SystemFieldResponse;
import com.moonstack.entity.SystemField;
import com.moonstack.utils.Helper;

public class SystemFieldMapper
{
    public static SystemField systemFieldRequestIntoSystemField(SystemFieldRequest request)
    {
        return SystemField.builder()
                .id(Helper.generateId())
                .addedBy(request.getAddedBy())
                .modifiedBy(request.getModifiedBy())
                .addedTime(request.getAddedTime())
                .modifiedTime(request.getModifiedTime())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static SystemFieldResponse systemFieldIntoSystemFieldReason(SystemField systemField)
    {
        return SystemFieldResponse.builder()
                .addedBy(systemField.getAddedBy())
                .modifiedBy(systemField.getModifiedBy())
                .addedTime(systemField.getAddedTime())
                .modifiedTime(systemField.getModifiedTime())
                .build();
    }
}
