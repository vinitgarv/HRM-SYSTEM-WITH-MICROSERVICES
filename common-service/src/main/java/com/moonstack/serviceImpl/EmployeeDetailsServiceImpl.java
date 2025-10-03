package com.moonstack.serviceImpl;

import com.moonstack.constants.Message;
import com.moonstack.dtos.request.*;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.entity.*;
import com.moonstack.exception.AlreadyPresentException;
import com.moonstack.mapper.*;
import com.moonstack.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        if (user == null) {
            throw new RuntimeException("User with id " + userId + " does not exist. Please register first.");
        }

        request.validate();

        userService.add(user);
        if (user.getWorkInfo() != null || user.getPersonalDetail() != null ||
                (user.getHierarchyInfos() != null && !user.getHierarchyInfos().isEmpty()) ||
                (user.getWorkExperiences() != null && !user.getWorkExperiences().isEmpty()) ||
                (user.getEducationDetails() != null && !user.getEducationDetails().isEmpty()) ||
                (user.getDependentDetails() != null && !user.getDependentDetails().isEmpty()) ||
                (user.getRelatedForms() != null && !user.getRelatedForms().isEmpty()) ||
                user.getIdentityInfo() != null || user.getContactDetail() != null || user.getSystemField() != null) {
            throw new AlreadyPresentException("Employee details already exist for this user. Use update method instead.");
        }

        if (request.getWorkInfo() != null) {
            WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
            workInfo.setUser(user);
            workInfoService.addWorkInfo(workInfo);
        }

        if (request.getHierarchyInfos() != null) {
            request.getHierarchyInfos().forEach(hiReq -> {
                HierarchyInfo hi = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hiReq);
                hi.setUser(user);
                hierarchyInfoService.addHierarchyInfo(hi);
            });
        }

        if (request.getPersonalDetail() != null) {
            PersonalDetail pd = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
            pd.setUser(user);
            personalDetailService.addPersonalDetail(pd);
        }

        if (request.getIdentityInfo() != null) {
            IdentityInfo ii = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
            ii.setUser(user);
            identityInfoService.addIdentityInfo(ii);
        }

        if (request.getContactDetail() != null) {
            ContactDetail cd = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
            cd.setUser(user);
            contactDetailService.addContactDetails(cd);
        }

        if (request.getSystemField() != null) {
            SystemField sf = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
            sf.setUser(user);
            systemFieldService.addSystemField(sf);
        }

        if (request.getWorkExperience() != null) {
            request.getWorkExperience().forEach(weReq -> {
                WorkExperience we = WorkExperienceMapper.workExperienceRequestIntoWorkExperience(weReq);
                we.setUser(user);
                workExperienceService.addWorkExperience(we);
            });
        }

        if (request.getEducationDetails() != null) {
            request.getEducationDetails().forEach(edReq -> {
                EducationDetail ed = EducationDetailMapper.educationDetailRequestIntoEducationDetail(edReq);
                ed.setUser(user);
                educationDetailService.addEducationalDetail(ed);
            });
        }

        if (request.getDependentDetails() != null) {
            request.getDependentDetails().forEach(ddReq -> {
                DependentDetail dd = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(ddReq);
                dd.setUser(user);
                dependentDetailService.addDependentDetails(dd);
            });
        }

        if (request.getRelatedForms() != null) {
            request.getRelatedForms().forEach(rfReq -> {
                RelatedForm rf = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rfReq);
                rf.setUser(user);
                relatedFormService.addRelatedForm(rf);
            });
        }

        return Message.USER + Message.TAB + Message.REGISTERED
                + Message.TAB + Message.SUCCESSFULLY + Message.DOT;
    }

    //    @Override
//    public String addEmployeeDetails(EmployeeDetailsRequest request, String userId) {
//        User user = userService.findById(userId);
//        userService.add(user);
//
//        WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
//        workInfo.setUser(user);
//        workInfoService.addWorkInfo(workInfo);
//
//        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
//                                                .map(hi -> {
//                                                    HierarchyInfo hierarchyInfo = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hi);
//                                                    hierarchyInfo.setUser(user);
//                                                    return hierarchyInfoService.addHierarchyInfo(hierarchyInfo);
//                                                }).toList();
//
//        PersonalDetail personalDetail = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
//        personalDetail.setUser(user);
//        personalDetailService.addPersonalDetail(personalDetail);
//
//        IdentityInfo identityInfo = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
//        identityInfo.setUser(user);
//        identityInfoService.addIdentityInfo(identityInfo);
//
//        ContactDetail contactDetail = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
//        contactDetail.setUser(user);
//        contactDetailService.addContactDetails(contactDetail);
//
//        SystemField systemField = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
//        systemField.setUser(user);
//        systemFieldService.addSystemField(systemField);
//
//        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
//                                                .map(we ->{
//                                                    WorkExperience workExperience =  WorkExperienceMapper.workExperienceRequestIntoWorkExperience(we);
//                                                    workExperience.setUser(user);
//                                                    return workExperienceService.addWorkExperience(workExperience);
//                                                }).toList();
//
//        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
//                                                .map(ed -> {
//                                                    EducationDetail educationDetail = EducationDetailMapper.educationDetailRequestIntoEducationDetail(ed);
//                                                    educationDetail.setUser(user);
//                                                    return educationDetailService.addEducationalDetail(educationDetail);
//                                                }).toList();
//
//        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
//                                                .map(dd -> {
//                                                    DependentDetail dependentDetail = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(dd);
//                                                    dependentDetail.setUser(user);
//                                                    return dependentDetailService.addDependentDetails(dependentDetail);
//                                                }).toList();
//
//        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
//                                                .map(rf -> {
//                                                    RelatedForm relatedForm = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rf);
//                                                    relatedForm.setUser(user);
//                                                    return relatedFormService.addRelatedForm(relatedForm);
//                                                }).toList();
//
//        return Message.USER+Message.TAB+Message.REGISTERED
//               +Message.TAB+Message.SUCCESSFULLY+Message.DOT;
//    }

    @Override
    public EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request) {
        User user = userService.findById(id);
        EmployeeDetailMapper.updateUserFromRequest(user, request);
        userService.add(user);
        return EmployeeDetailMapper.toResponse(user);
    }

    @Override
    public EmployeeDetailsResponse getById(String id) {
        User user = userService.getById(id);
        return EmployeeDetailMapper.toResponse(user);
    }
}
