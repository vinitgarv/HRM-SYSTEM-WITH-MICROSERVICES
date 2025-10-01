package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.UserResponse;
import com.moonstack.entity.*;
import com.moonstack.mapper.*;
import com.moonstack.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkInfoService workInfoService;

    @Autowired
    private HierarchyInfoService hierarchyInfoService;

    @Autowired
    private PersonalDetailService personalDetailService;

    @Autowired
    private IdentityInfoService identityInfoService;

    @Autowired
    private ContactDetailService contactDetailService;

    @Autowired
    private SystemFieldService systemFieldService;

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private EducationDetailService educationDetailService;

    @Autowired
    private DependentDetailService dependentDetailService;

    @Autowired
    private RelatedFormService relatedFormService;

    @Override
    public String addEmployeeDetails(EmployeeDetailsRequest request, String userId) {
        User user = userService.findById(userId);
        userService.add(user);

        WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
        workInfo.setUser(user);
        workInfoService.addWorkInfo(workInfo);

        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
                                                .map(hi -> {
                                                    HierarchyInfo hierarchyInfo = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hi);
                                                    hierarchyInfo.setUser(user);
                                                    return hierarchyInfoService.addHierarchyInfo(hierarchyInfo);
                                                }).toList();

        PersonalDetail personalDetail = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
        personalDetail.setUser(user);
        personalDetailService.addPersonalDetail(personalDetail);

        IdentityInfo identityInfo = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
        identityInfo.setUser(user);
        identityInfoService.addIdentityInfo(identityInfo);

        ContactDetail contactDetail = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
        contactDetail.setUser(user);
        contactDetailService.addContactDetails(contactDetail);

        SystemField systemField = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
        systemField.setUser(user);
        systemFieldService.addSystemField(systemField);

        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
                                                .map(we ->{
                                                    WorkExperience workExperience =  WorkExperienceMapper.workExperienceRequestIntoWorkExperience(we);
                                                    workExperience.setUser(user);
                                                    return workExperienceService.addWorkExperience(workExperience);
                                                }).toList();

        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
                                                .map(ed -> {
                                                    EducationDetail educationDetail = EducationDetailMapper.educationDetailRequestIntoEducationDetail(ed);
                                                    educationDetail.setUser(user);
                                                    return educationDetailService.addEducationalDetail(educationDetail);
                                                }).toList();

        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
                                                .map(dd -> {
                                                    DependentDetail dependentDetail = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(dd);
                                                    dependentDetail.setUser(user);
                                                    return dependentDetailService.addDependentDetails(dependentDetail);
                                                }).toList();

        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
                                                .map(rf -> {
                                                    RelatedForm relatedForm = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rf);
                                                    relatedForm.setUser(user);
                                                    return relatedFormService.addRelatedForm(relatedForm);
                                                }).toList();

        return Message.USER+Message.TAB+Message.REGISTERED
               +Message.TAB+Message.SUCCESSFULLY+Message.DOT;
    }

    @Override
    @Transactional
    public EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request) {

        User user = userService.findById(id);

        if (user.getWorkInfo() != null) {
            WorkInfoMapper.updateFromRequest(request.getWorkInfo(), user.getWorkInfo());
        } else {
            WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
            workInfo.setUser(user);
            user.setWorkInfo(workInfo);
        }

        user.getHierarchyInfos().clear();
        request.getHierarchyInfos().forEach(hi -> {
            HierarchyInfo hierarchyInfo = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hi);
            hierarchyInfo.setUser(user);
            user.getHierarchyInfos().add(hierarchyInfo);
        });

        if (user.getPersonalDetail() != null) {
            PersonalDetailMapper.updateFromRequest(request.getPersonalDetail(), user.getPersonalDetail());
        } else {
            PersonalDetail personalDetail = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
            personalDetail.setUser(user);
            user.setPersonalDetail(personalDetail);
        }

        if (user.getIdentityInfo() != null) {
            IdentityInfoMapper.updateFromRequest(request.getIdentityInfo(), user.getIdentityInfo());
        } else {
            IdentityInfo identityInfo = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
            identityInfo.setUser(user);
            user.setIdentityInfo(identityInfo);
        }

        if (user.getContactDetail() != null) {
            ContactDetailMapper.updateFromRequest(request.getContactDetail(), user.getContactDetail());
        } else {
            ContactDetail contactDetail = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
            contactDetail.setUser(user);
            user.setContactDetail(contactDetail);
        }

        if (user.getSystemField() != null) {
            SystemFieldMapper.updateFromRequest(request.getSystemField(), user.getSystemField());
        } else {
            SystemField systemField = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
            systemField.setUser(user);
            user.setSystemField(systemField);
        }

        user.getWorkExperiences().clear();
        request.getWorkExperience().forEach(we -> {
            WorkExperience workExperience = WorkExperienceMapper.workExperienceRequestIntoWorkExperience(we);
            workExperience.setUser(user);
            user.getWorkExperiences().add(workExperience);
        });

        user.getEducationDetails().clear();
        request.getEducationDetails().forEach(ed -> {
            EducationDetail educationDetail = EducationDetailMapper.educationDetailRequestIntoEducationDetail(ed);
            educationDetail.setUser(user);
            user.getEducationDetails().add(educationDetail);
        });

        user.getDependentDetails().clear();
        request.getDependentDetails().forEach(dd -> {
            DependentDetail dependentDetail = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(dd);
            dependentDetail.setUser(user);
            user.getDependentDetails().add(dependentDetail);
        });

        user.getRelatedForms().clear();
        request.getRelatedForms().forEach(rf -> {
            RelatedForm relatedForm = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rf);
            relatedForm.setUser(user);
            user.getRelatedForms().add(relatedForm);
        });

        userService.add(user);

        return EmployeeDetailsResponse.builder()

                .userResponse(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .workInfo(WorkInfoMapper.workInfoIntoWorkInfoResponse(user.getWorkInfo()))
                .hierarchyInfos(user.getHierarchyInfos().stream()
                        .map(HierarchyInfoMapper::hierarchyInfoIntoHierarchyInfoResponse)
                        .toList())
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
                .build();
    }


    @Override
    public EmployeeDetailsResponse getById(String id) {
        User user = userService.getById(id);

        return EmployeeDetailsResponse.builder()
                .userResponse(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())

                .workInfo(WorkInfoMapper.workInfoIntoWorkInfoResponse(user.getWorkInfo()))
                .hierarchyInfos(user.getHierarchyInfos().stream()
                        .map(HierarchyInfoMapper::hierarchyInfoIntoHierarchyInfoResponse)
                        .toList())
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
                .build();
    }

}
