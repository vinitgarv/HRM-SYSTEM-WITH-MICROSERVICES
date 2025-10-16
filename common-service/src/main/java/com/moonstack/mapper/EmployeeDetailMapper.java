package com.moonstack.mapper;

import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.dtos.response.RoleResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.*;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDetailMapper {

    public static EmployeeDetailsResponse toResponse(User user) {
        Set<RoleResponse> roles = user.getRoles().stream()
                .map(UserMapper::convertRoletoRoleResponse)
                .collect(Collectors.toSet());

        return EmployeeDetailsResponse.builder()
                .userResponse(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .roles(roles)
                        .build())

                .profilePhoto(
                        FileUploadResponse.builder()
                                .fileName(user.getProfilePhotoFileName())
                                .fileType(user.getResumeFileType())
                                .build()
                )

                .resume(
                        FileUploadResponse.builder()
                                .fileName(user.getResumeFileName())
                                .fileType(user.getResumeFileType())
                                .build()
                )

                .workInfo(WorkInfoMapper.workInfoIntoWorkInfoResponse(user.getWorkInfo()))

                .personalDetail(PersonalDetailMapper.personalDetailIntoPersonalDetailResponse(user.getPersonalDetail()))


                .contactDetail(ContactDetailMapper.contactDetailIntoContactDetailResponse(user.getContactDetail()))

                .systemField(SystemFieldMapper.systemFieldIntoSystemFieldReason(user.getSystemField()))

                .workExperience(user.getWorkExperiences().stream()
                        .map(WorkExperienceMapper::workExperienceIntoWorkExperienceResponse)
                        .toList())

                .identityInfo(user.getIdentityInfo().stream()
                        .map(IdentityInfoMapper::identityInfoIntoIdentityInfoResponse)
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
