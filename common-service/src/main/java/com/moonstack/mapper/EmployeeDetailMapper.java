package com.moonstack.mapper;

import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.*;
import java.util.ArrayList;

public class EmployeeDetailMapper {

    public static void updateUserFromRequest(User user, EmployeeDetailsRequest request) {
        if (request.getWorkInfo() != null) {
            if (user.getWorkInfo() != null) {
                WorkInfoMapper.updateFromRequest(request.getWorkInfo(), user.getWorkInfo());
            } else {
                WorkInfo wi = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
                wi.setUser(user);
                user.setWorkInfo(wi);
            }
        }

        if (request.getPersonalDetail() != null) {
            if (user.getPersonalDetail() != null) {
                PersonalDetailMapper.updateFromRequest(request.getPersonalDetail(), user.getPersonalDetail());
            } else {
                PersonalDetail pd = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
                pd.setUser(user);
                user.setPersonalDetail(pd);
            }
        }

        if (request.getIdentityInfo() != null) {
            if (user.getIdentityInfo() != null) {
                IdentityInfoMapper.updateFromRequest(request.getIdentityInfo(), user.getIdentityInfo());
            } else {
                IdentityInfo ii = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
                ii.setUser(user);
                user.setIdentityInfo(ii);
            }
        }

        if (request.getContactDetail() != null) {
            if (user.getContactDetail() != null) {
                ContactDetailMapper.updateFromRequest(request.getContactDetail(), user.getContactDetail());
            } else {
                ContactDetail cd = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
                cd.setUser(user);
                user.setContactDetail(cd);
            }
        }

        if (request.getSystemField() != null) {
            if (user.getSystemField() != null) {
                SystemFieldMapper.updateFromRequest(request.getSystemField(), user.getSystemField());
            } else {
                SystemField sf = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
                sf.setUser(user);
                user.setSystemField(sf);
            }
        }

        if (request.getWorkExperience() != null) {
            if (user.getWorkExperiences() == null) user.setWorkExperiences(new ArrayList<>());
            user.getWorkExperiences().clear();
            request.getWorkExperience().forEach(req -> {
                WorkExperience we = WorkExperienceMapper.workExperienceRequestIntoWorkExperience(req);
                we.setUser(user);
                user.getWorkExperiences().add(we);
            });
        }

        if (request.getEducationDetails() != null) {
            if (user.getEducationDetails() == null) user.setEducationDetails(new ArrayList<>());
            user.getEducationDetails().clear();
            request.getEducationDetails().forEach(req -> {
                EducationDetail ed = EducationDetailMapper.educationDetailRequestIntoEducationDetail(req);
                ed.setUser(user);
                user.getEducationDetails().add(ed);
            });
        }

        if (request.getDependentDetails() != null) {
            if (user.getDependentDetails() == null) user.setDependentDetails(new ArrayList<>());
            user.getDependentDetails().clear();
            request.getDependentDetails().forEach(req -> {
                DependentDetail dd = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(req);
                dd.setUser(user);
                user.getDependentDetails().add(dd);
            });
        }

        if (request.getRelatedForms() != null) {
            if (user.getRelatedForms() == null) user.setRelatedForms(new ArrayList<>());
            user.getRelatedForms().clear();
            request.getRelatedForms().forEach(req -> {
                RelatedForm rf = RelatedFormMapper.relatedFormRequestIntoRelatedForm(req);
                rf.setUser(user);
                user.getRelatedForms().add(rf);
            });
        }

        if (request.getHierarchyInfos() != null) {
            if (user.getHierarchyInfos() == null) user.setHierarchyInfos(new ArrayList<>());
            user.getHierarchyInfos().clear();
            request.getHierarchyInfos().forEach(req -> {
                HierarchyInfo hi = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(req);
                hi.setUser(user);
                user.getHierarchyInfos().add(hi);
            });
        }
    }

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
