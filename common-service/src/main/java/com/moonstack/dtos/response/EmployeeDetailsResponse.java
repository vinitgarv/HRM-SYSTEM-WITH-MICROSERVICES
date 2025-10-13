package com.moonstack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsResponse
{
    private UserResponse userResponse;
    private WorkInfoResponse workInfo;
    private List<HierarchyInfoResponse> hierarchyInfos;
    private PersonalDetailResponse personalDetail;
    private List<IdentityInfoResponse> identityInfo;
    private ContactDetailResponse contactDetail;
    private SystemFieldResponse systemField;
    private List<WorkExperienceResponse> workExperience;
    private List<EducationDetailResponse> educationDetails;
    private List<DependentDetailResponse> dependentDetails;
    private List<RelatedFormResponse> relatedForms;
}
