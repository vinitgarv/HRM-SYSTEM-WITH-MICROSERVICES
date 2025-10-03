package com.moonstack.mapper;

import com.moonstack.dtos.request.WorkExperienceRequest;
import com.moonstack.dtos.request.WorkInfoRequest;
import com.moonstack.dtos.response.WorkExperienceResponse;
import com.moonstack.entity.WorkExperience;
import com.moonstack.entity.WorkInfo;
import com.moonstack.utils.Helper;

public class WorkExperienceMapper
{
    public static WorkExperience workExperienceRequestIntoWorkExperience(WorkExperienceRequest request)
    {
        return WorkExperience.builder()
                .id(Helper.generateId())
                .companyName(request.getCompanyName())
                .jobTitle(request.getJobTitle())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .jobDescription(request.getJobDescription())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static void updateFromRequest(WorkExperienceRequest request, WorkExperience workExperience) {
        workExperience.setCompanyName(request.getCompanyName());
        workExperience.setJobTitle(request.getJobTitle());
        workExperience.setFromDate(request.getFromDate());
        workExperience.setToDate(request.getToDate());
        workExperience.setJobDescription(request.getJobDescription());
    }


    public static WorkExperienceResponse workExperienceIntoWorkExperienceResponse(WorkExperience workExperience)
    {
        return WorkExperienceResponse.builder()
                .companyName(workExperience.getCompanyName())
                .jobTitle(workExperience.getJobTitle())
                .fromDate(workExperience.getFromDate())
                .toDate(workExperience.getToDate())
                .jobDescription(workExperience.getJobDescription())
                .build();
    }
}
