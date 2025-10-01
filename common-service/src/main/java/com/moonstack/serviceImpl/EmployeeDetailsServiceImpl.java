package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.entity.*;
import com.moonstack.mapper.*;
import com.moonstack.repository.*;
import com.moonstack.service.EmployeeDetailsService;
import com.moonstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {


    @Autowired
    private UserService userService;

    @Autowired
    private WorkInfoRepository workInfoRepository;

    @Autowired
    private HierarchyInfoRepository hierarchyInfoRepository;

    @Autowired
    private PersonalDetailRepository personalDetailRepository;

    @Autowired
    private IdentityInfoRepository identityInfoRepository;

    @Autowired
    private ContactDetailRepository contactDetailRepository;

    @Autowired
    private SystemFieldRepository systemFieldRepository;

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private EducationDetailRepository educationDetailRepository;

    @Autowired
    private DependentDetailRepository dependentDetailRepository;

    @Autowired
    private RelatedFormRepository relatedFormRepository;


    @Override
    public String addEmployeeDetails(EmployeeDetailsRequest request, String userId) {

        User user = userService.findById(userId);

        request.validate();

        userService.add(user);

        WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
        workInfo.setUser(user);
        workInfoRepository.save(workInfo);

        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
                                                .map(hi -> {
                                                    HierarchyInfo hierarchyInfo = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hi);
                                                    hierarchyInfo.setUser(user);
                                                    return hierarchyInfoRepository.save(hierarchyInfo);
                                                }).toList();

        PersonalDetail personalDetail = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
        personalDetail.setUser(user);
        personalDetailRepository.save(personalDetail);

        IdentityInfo identityInfo = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
        identityInfo.setUser(user);
        identityInfoRepository.save(identityInfo);

        ContactDetail contactDetail = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
        contactDetail.setUser(user);
        contactDetailRepository.save(contactDetail);

        SystemField systemField = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
        systemField.setUser(user);
        systemFieldRepository.save(systemField);

        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
                                                .map(we ->{
                                                    WorkExperience workExperience =  WorkExperienceMapper.workExperienceRequestIntoWorkExperience(we);
                                                    workExperience.setUser(user);
                                                    return workExperienceRepository.save(workExperience);
                                                }).toList();

        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
                                                .map(ed -> {
                                                    EducationDetail educationDetail = EducationDetailMapper.educationDetailRequestIntoEducationDetail(ed);
                                                    educationDetail.setUser(user);
                                                    return educationDetailRepository.save(educationDetail);
                                                }).toList();

        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
                                                .map(dd -> {
                                                    DependentDetail dependentDetail = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(dd);
                                                    dependentDetail.setUser(user);
                                                    return dependentDetailRepository.save(dependentDetail);
                                                }).toList();

        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
                                                .map(rf -> {
                                                    RelatedForm relatedForm = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rf);
                                                    relatedForm.setUser(user);
                                                    return relatedFormRepository.save(relatedForm);
                                                }).toList();
//
//        user.setWorkInfo(workInfo);
//        user.setHierarchyInfos(hierarchyInfos);
//        user.setPersonalDetail(personalDetail);
//        user.setIdentityInfo(identityInfo);
//        user.setContactDetail(contactDetail);
//        user.setSystemField(systemField);
//        user.setWorkExperiences(workExperiences);
//        user.setEducationDetails(educationDetails);
//        user.setDependentDetails(dependentDetails);
//        user.setRelatedForms(relatedForms);
        return Message.USER+Message.TAB+Message.REGISTERED
               +Message.TAB+Message.SUCCESSFULLY+Message.DOT;
    }

//    @Override
//    public EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request) {
//
//        BasicInfo existingBasicInfo = basicInfoService.getById(id);
//        existingBasicInfo.setNickname(request.getBasicInfo().getNickname());
//        existingBasicInfo.setFirstname(request.getBasicInfo().getFirstname());
//        existingBasicInfo.setLastname(request.getBasicInfo().getLastname());
//
//
//        WorkInfo workInfo = WorkInfoTransformer.workInfoRequestIntoWorkInfo(request.getWorkInfo());
//        workInfo.setBasicInfo(existingBasicInfo);
//
//        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
//                .map(hi -> {
//                    HierarchyInfo hierarchyInfo = HierarchyInfoTransformer.hierarchyInfoRequestIntoHierarchyInfo(hi);
//                    hierarchyInfo.setBasicInfo(existingBasicInfo);
//                    return hierarchyInfo;
//                }).toList();
//
//        PersonalDetail personalDetail = PersonalDetailTransformer.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
//        personalDetail.setBasicInfo(existingBasicInfo);
//
//        IdentityInfo identityInfo = IdentityInfoTransformer.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
//        identityInfo.setBasicInfo(existingBasicInfo);
//
//        ContactDetail contactDetail = ContactDetailTransformer.contactDetailRequestIntoContactDetail(request.getContactDetail());
//        contactDetail.setBasicInfo(existingBasicInfo);
//
//        SystemField systemField = SystemFieldTransformer.systemFieldRequestIntoSystemField(request.getSystemField());
//        systemField.setBasicInfo(existingBasicInfo);
//
//        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
//                .map(we ->{
//                    WorkExperience workExperience =  WorkExperienceTransformer.workExperienceRequestIntoWorkExperience(we);
//                    workExperience.setBasicInfo(existingBasicInfo);
//                    return workExperience;
//                }).toList();
//
//        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
//                .map(ed -> {
//                    EducationDetail educationDetail = EducationDetailTransformer.educationDetailRequestIntoEducationDetail(ed);
//                    educationDetail.setBasicInfo(existingBasicInfo);
//                    return educationDetail;
//                }).toList();
//
//        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
//                .map(dd -> {
//                    DependentDetail dependentDetail = DependentDetailTransformer.dependentDetailRequestIntoDependentDetail(dd);
//                    dependentDetail.setBasicInfo(existingBasicInfo);
//                    return dependentDetail;
//                }).toList();
//
//        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
//                .map(rf -> {
//                    RelatedForm relatedForm = RelatedFormTransformer.relatedFormRequestIntoRelatedForm(rf);
//                    relatedForm.setBasicInfo(existingBasicInfo);
//                    return relatedForm;
//                }).toList();
//
//        existingBasicInfo.setWorkInfo(workInfo);
//        existingBasicInfo.setHierarchyInfos(hierarchyInfos);
//        existingBasicInfo.setPersonalDetail(personalDetail);
//        existingBasicInfo.setIdentityInfo(identityInfo);
//        existingBasicInfo.setContactDetail(contactDetail);
//        existingBasicInfo.setSystemField(systemField);
//        existingBasicInfo.setWorkExperiences(workExperiences);
//        existingBasicInfo.setEducationDetails(educationDetails);
//        existingBasicInfo.setDependentDetails(dependentDetails);
//        existingBasicInfo.setRelatedForms(relatedForms);
//
//        basicInfoService.add(existingBasicInfo);
//
//
//        return EmployeeDetailsResponse.builder()
//                .basicInfo(BasicInfoTransformer.basicInfoIntoBasicInfoResponse(existingBasicInfo))
//                .workInfo(WorkInfoTransformer.workInfoIntoWorkInfoResponse(existingBasicInfo.getWorkInfo()))
//                .hierarchyInfos(existingBasicInfo.getHierarchyInfos().stream()
//                                        .map(hi -> HierarchyInfoTransformer.hierarchyInfoIntoHierarchyInfoResponse(hi))
//                                        .toList())
//                .personalDetail(PersonalDetailTransformer.personalDetailIntoPersonalDetailResponse(existingBasicInfo.getPersonalDetail()))
//                .identityInfo(IdentityInfoTransformer.identityInfoIntoIdentityInfoResponse(existingBasicInfo.getIdentityInfo()))
//                .contactDetail(ContactDetailTransformer.contactDetailIntoContactDetailResponse(existingBasicInfo.getContactDetail()))
//                .systemField(SystemFieldTransformer.systemFieldIntoSystemFieldReason(existingBasicInfo.getSystemField()))
//                .workExperience(existingBasicInfo.getWorkExperiences().stream()
//                                        .map(we -> WorkExperienceTransformer.workExperienceIntoWorkExperienceResponse(we))
//                                        .toList())
//                .educationDetails(existingBasicInfo.getEducationDetails().stream()
//                                        .map(ed -> EducationDetailTransformer.educationDetailIntoEducationDetailResponse(ed))
//                                        .toList())
//                .dependentDetails(existingBasicInfo.getDependentDetails().stream()
//                                        .map(dd -> DependentDetailTransformer.dependentDetailIntoDependentDetailResponse(dd))
//                                        .toList())
//                .relatedForms(existingBasicInfo.getRelatedForms().stream()
//                                        .map(rf -> RelatedFormTransformer.relatedFormIntoRelatedFormResponse(rf))
//                                        .toList())
//                .build();
//    }

//    @Override
//    public EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request) {
//
//        BasicInfo existingBasicInfo = basicInfoService.getById(id);
//
//        // update basic info fields
//        existingBasicInfo.setNickname(request.getBasicInfo().getNickname());
//        existingBasicInfo.setFirstname(request.getBasicInfo().getFirstname());
//        existingBasicInfo.setLastname(request.getBasicInfo().getLastname());
//
//        // WorkInfo
//        WorkInfo workInfo = WorkInfoTransformer.workInfoRequestIntoWorkInfo(request.getWorkInfo());
//        workInfo.setBasicInfo(existingBasicInfo);
//        existingBasicInfo.setWorkInfo(workInfo);
//
//        // Hierarchy Infos
//        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
//                .map(hi -> {
//                    HierarchyInfo hierarchyInfo = HierarchyInfoTransformer.hierarchyInfoRequestIntoHierarchyInfo(hi);
//                    hierarchyInfo.setBasicInfo(existingBasicInfo);
//                    return hierarchyInfo;
//                }).toList();
//        existingBasicInfo.getHierarchyInfos().clear();
//        existingBasicInfo.getHierarchyInfos().addAll(hierarchyInfos);
//
//        // Personal Detail
//        PersonalDetail personalDetail = PersonalDetailTransformer.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
//        personalDetail.setBasicInfo(existingBasicInfo);
//        existingBasicInfo.setPersonalDetail(personalDetail);
//
//        // Identity Info
//        IdentityInfo identityInfo = IdentityInfoTransformer.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
//        identityInfo.setBasicInfo(existingBasicInfo);
//        existingBasicInfo.setIdentityInfo(identityInfo);
//
//        // Contact Detail
//        ContactDetail contactDetail = ContactDetailTransformer.contactDetailRequestIntoContactDetail(request.getContactDetail());
//        contactDetail.setBasicInfo(existingBasicInfo);
//        existingBasicInfo.setContactDetail(contactDetail);
//
//        // System Field
//        SystemField systemField = SystemFieldTransformer.systemFieldRequestIntoSystemField(request.getSystemField());
//        systemField.setBasicInfo(existingBasicInfo);
//        existingBasicInfo.setSystemField(systemField);
//
//        // Work Experiences
//        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
//                .map(we -> {
//                    WorkExperience workExperience = WorkExperienceTransformer.workExperienceRequestIntoWorkExperience(we);
//                    workExperience.setBasicInfo(existingBasicInfo);
//                    return workExperience;
//                }).toList();
//        existingBasicInfo.getWorkExperiences().clear();
//        existingBasicInfo.getWorkExperiences().addAll(workExperiences);
//
//        // Education Details
//        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
//                .map(ed -> {
//                    EducationDetail educationDetail = EducationDetailTransformer.educationDetailRequestIntoEducationDetail(ed);
//                    educationDetail.setBasicInfo(existingBasicInfo);
//                    return educationDetail;
//                }).toList();
//        existingBasicInfo.getEducationDetails().clear();
//        existingBasicInfo.getEducationDetails().addAll(educationDetails);
//
//        // Dependent Details
//        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
//                .map(dd -> {
//                    DependentDetail dependentDetail = DependentDetailTransformer.dependentDetailRequestIntoDependentDetail(dd);
//                    dependentDetail.setBasicInfo(existingBasicInfo);
//                    return dependentDetail;
//                }).toList();
//        existingBasicInfo.getDependentDetails().clear();
//        existingBasicInfo.getDependentDetails().addAll(dependentDetails);
//
//        // Related Forms
//        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
//                .map(rf -> {
//                    RelatedForm relatedForm = RelatedFormTransformer.relatedFormRequestIntoRelatedForm(rf);
//                    relatedForm.setBasicInfo(existingBasicInfo);
//                    return relatedForm;
//                }).toList();
//        existingBasicInfo.getRelatedForms().clear();
//        existingBasicInfo.getRelatedForms().addAll(relatedForms);
//
//        // save updated object
//        basicInfoService.add(existingBasicInfo);
//
//        // build response
//        return EmployeeDetailsResponse.builder()
//                .basicInfo(BasicInfoTransformer.basicInfoIntoBasicInfoResponse(existingBasicInfo))
//                .workInfo(WorkInfoTransformer.workInfoIntoWorkInfoResponse(existingBasicInfo.getWorkInfo()))
//                .hierarchyInfos(existingBasicInfo.getHierarchyInfos().stream()
//                        .map(HierarchyInfoTransformer::hierarchyInfoIntoHierarchyInfoResponse)
//                        .toList())
//                .personalDetail(PersonalDetailTransformer.personalDetailIntoPersonalDetailResponse(existingBasicInfo.getPersonalDetail()))
//                .identityInfo(IdentityInfoTransformer.identityInfoIntoIdentityInfoResponse(existingBasicInfo.getIdentityInfo()))
//                .contactDetail(ContactDetailTransformer.contactDetailIntoContactDetailResponse(existingBasicInfo.getContactDetail()))
//                .systemField(SystemFieldTransformer.systemFieldIntoSystemFieldReason(existingBasicInfo.getSystemField()))
//                .workExperience(existingBasicInfo.getWorkExperiences().stream()
//                        .map(WorkExperienceTransformer::workExperienceIntoWorkExperienceResponse)
//                        .toList())
//                .educationDetails(existingBasicInfo.getEducationDetails().stream()
//                        .map(EducationDetailTransformer::educationDetailIntoEducationDetailResponse)
//                        .toList())
//                .dependentDetails(existingBasicInfo.getDependentDetails().stream()
//                        .map(DependentDetailTransformer::dependentDetailIntoDependentDetailResponse)
//                        .toList())
//                .relatedForms(existingBasicInfo.getRelatedForms().stream()
//                        .map(RelatedFormTransformer::relatedFormIntoRelatedFormResponse)
//                        .toList())
//                .build();
//    }
//
//    @Override
//    public EmployeeDetailsResponse getById(String id)
//    {
//        BasicInfo basicInfo = basicInfoService.getById(id);
//
//        return EmployeeDetailsResponse.builder()
//                .basicInfo(BasicInfoTransformer.basicInfoIntoBasicInfoResponse(basicInfo))
//                .workInfo(WorkInfoTransformer.workInfoIntoWorkInfoResponse(basicInfo.getWorkInfo()))
//                .hierarchyInfos(basicInfo.getHierarchyInfos().stream()
//                        .map(HierarchyInfoTransformer::hierarchyInfoIntoHierarchyInfoResponse)
//                        .toList())
//                .personalDetail(PersonalDetailTransformer.personalDetailIntoPersonalDetailResponse(basicInfo.getPersonalDetail()))
//                .identityInfo(IdentityInfoTransformer.identityInfoIntoIdentityInfoResponse(basicInfo.getIdentityInfo()))
//                .contactDetail(ContactDetailTransformer.contactDetailIntoContactDetailResponse(basicInfo.getContactDetail()))
//                .systemField(SystemFieldTransformer.systemFieldIntoSystemFieldReason(basicInfo.getSystemField()))
//                .workExperience(basicInfo.getWorkExperiences().stream()
//                        .map(WorkExperienceTransformer::workExperienceIntoWorkExperienceResponse)
//                        .toList())
//                .educationDetails(basicInfo.getEducationDetails().stream()
//                        .map(EducationDetailTransformer::educationDetailIntoEducationDetailResponse)
//                        .toList())
//                .dependentDetails(basicInfo.getDependentDetails().stream()
//                        .map(DependentDetailTransformer::dependentDetailIntoDependentDetailResponse)
//                        .toList())
//                .relatedForms(basicInfo.getRelatedForms().stream()
//                        .map(RelatedFormTransformer::relatedFormIntoRelatedFormResponse)
//                        .toList())
//                .build();
//    }

}
