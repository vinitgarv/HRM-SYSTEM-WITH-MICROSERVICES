package com.moonstack.dtos.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsRequest {

    private WorkInfoRequest workInfo;
    private List<HierarchyInfoRequest> hierarchyInfos;
    private PersonalDetailRequest personalDetail;
    private IdentityInfoRequest identityInfo;
    private ContactDetailRequest contactDetail;
    private SystemFieldRequest systemField;
    private List<WorkExperienceRequest> workExperience;
    private List<EducationDetailRequest> educationDetails;
    private List<DependentDetailRequest> dependentDetails;
    private List<RelatedFormRequest> relatedForms;
}
