package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsRequest
{
    private DocumentRequest profilePhoto;
    private DocumentRequest resume;
    private WorkInfoRequest workInfo;
    private List<HierarchyInfoRequest> hierarchyInfos;
    private PersonalDetailRequest personalDetail;
    private List<IdentityInfoRequest> identityInfo; 
    private ContactDetailRequest contactDetail;
    private SystemFieldRequest systemField;
    private List<WorkExperienceRequest> workExperience;
    private List<EducationDetailRequest> educationDetails;
    private List<DependentDetailRequest> dependentDetails;
    private List<RelatedFormRequest> relatedForms;

    public  void validate()
    {
        if (workInfo == null)
        {
            throw new RequestFailedException(Message.WORK_INFO + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        workInfo.validate();

        if (personalDetail == null) {
            throw new RequestFailedException(Message.PERSONAL_DETAIL + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        personalDetail.validate();

        if (contactDetail == null) {
            throw new RequestFailedException(Message.CONTACT_DETAIL + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        contactDetail.validate();

        if (systemField == null) {
            throw new RequestFailedException(Message.SYSTEM_FIELD + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }
        systemField.validate();

        // Validate hierarchy info list
        if (hierarchyInfos != null) {
            hierarchyInfos.forEach(HierarchyInfoRequest::validate);
        }

        // Validate work experience list
        if (workExperience != null) {
            workExperience.forEach(WorkExperienceRequest::validate);
        }

        // Validate education details list
        if (educationDetails != null) {
            educationDetails.forEach(EducationDetailRequest::validate);
        }

        // Validate dependent details list
        if (dependentDetails != null) {
            dependentDetails.forEach(DependentDetailRequest::validate);
        }

        // Validate related forms list
        if (relatedForms != null) {
            relatedForms.forEach(RelatedFormRequest::validate);
        }
    }
}
