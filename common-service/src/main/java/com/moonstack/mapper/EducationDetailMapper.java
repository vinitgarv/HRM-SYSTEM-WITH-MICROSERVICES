package com.moonstack.mapper;

import com.moonstack.dtos.request.EducationDetailRequest;
import com.moonstack.dtos.request.IdentityInfoRequest;
import com.moonstack.dtos.response.EducationDetailResponse;
import com.moonstack.entity.EducationDetail;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.utils.Helper;

public class EducationDetailMapper
{
    public static EducationDetail educationDetailRequestIntoEducationDetail(EducationDetailRequest request)
    {
        return EducationDetail.builder()
                .id(Helper.generateId())
                .instituteName(request.getInstituteName())
                .degreeOrDiploma(request.getDegreeOrDiploma())
                .specialization(request.getSpecialization())
                .completionDate(request.getCompletionDate())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static void updateFromRequest(EducationDetailRequest request, EducationDetail educationDetail) {
        educationDetail.setInstituteName(request.getInstituteName());
        educationDetail.setDegreeOrDiploma(request.getDegreeOrDiploma());
        educationDetail.setSpecialization(request.getSpecialization());
        educationDetail.setCompletionDate(request.getCompletionDate());
    }

    public static EducationDetailResponse educationDetailIntoEducationDetailResponse(EducationDetail educationDetail)
    {
        return EducationDetailResponse.builder()
                .instituteName(educationDetail.getInstituteName())
                .degreeOrDiploma(educationDetail.getDegreeOrDiploma())
                .specialization(educationDetail.getSpecialization())
                .completionDate(educationDetail.getCompletionDate())
                .build();
    }
}
