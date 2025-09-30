package com.moonstack.mapper;

import com.moonstack.dtos.request.RelatedFormRequest;
import com.moonstack.dtos.response.RelatedFormResponse;
import com.moonstack.entity.RelatedForm;
import com.moonstack.utils.Helper;

public class RelatedFormMapper
{
    public static RelatedForm relatedFormRequestIntoRelatedForm(RelatedFormRequest request)
    {
        return RelatedForm.builder()
                .id(Helper.generateId())
                .type(request.getType())
                .details(request.getDetails())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static RelatedFormResponse relatedFormIntoRelatedFormResponse(RelatedForm relatedForm)
    {
        return RelatedFormResponse.builder()

                .type(relatedForm.getType())
                .details(relatedForm.getDetails())
                .build();
    }
}
