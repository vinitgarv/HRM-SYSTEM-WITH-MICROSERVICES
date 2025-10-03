package com.moonstack.mapper;

import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.*;

public class EmployeeDetailMapper {

    public static EmployeeDetailsResponse toResponse(User user) {
        return EmployeeDetailsResponse.builder()
                .userResponse(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())

                .workInfo(WorkInfoMapper.workInfoIntoWorkInfoResponse(user.getWorkInfo()))

                .personalDetail(PersonalDetailMapper.personalDetailIntoPersonalDetailResponse(user.getPersonalDetail()))

                .identityInfo(IdentityInfoMapper.identityInfoIntoIdentityInfoResponse(user.getIdentityInfo()))

                .contactDetail(ContactDetailMapper.contactDetailIntoContactDetailResponse(user.getContactDetail()))

                .systemField(SystemFieldMapper.systemFieldIntoSystemFieldReason(user.getSystemField()))

                .workExperience(user.getWorkExperiences().stream()
                        .map(WorkExperienceMapper::workExperienceIntoWorkExperienceResponse)
                        .toList())

                .educationDetails(user.getEducationDetails().stream()
                        .map(EducationDetailMapper::educationDetailIntoEducationDetailResponse)
                        .toList())

                .dependentDetails(user.getDependentDetails().stream()
                        .map(DependentDetailMapper::dependentDetailIntoDependentDetailResponse)
                        .toList())

                .relatedForms(user.getRelatedForms().stream()
                        .map(RelatedFormMapper::relatedFormIntoRelatedFormResponse)
                        .toList())

                .hierarchyInfos(user.getHierarchyInfos().stream()
                        .map(HierarchyInfoMapper::hierarchyInfoIntoHierarchyInfoResponse)
                        .toList())
                .build();
    }
}
