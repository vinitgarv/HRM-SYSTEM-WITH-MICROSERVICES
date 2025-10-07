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
        if ( user.getWorkInfo()!= null)
            throw new AlreadyPresentException("Already exists");
        request.validate();
        userService.add(user);

            WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
            workInfo.setUser(user);
            workInfoService.addWorkInfo(workInfo);


            request.getHierarchyInfos().forEach(hiReq -> {
                HierarchyInfo hi = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hiReq);
                hi.setUser(user);
                hierarchyInfoService.addHierarchyInfo(hi);
            });


            PersonalDetail pd = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
            pd.setUser(user);
            personalDetailService.addPersonalDetail(pd);


            IdentityInfo ii = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
            ii.setUser(user);
            identityInfoService.addIdentityInfo(ii);


            ContactDetail cd = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
            cd.setUser(user);
            contactDetailService.addContactDetails(cd);


            SystemField sf = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
            sf.setUser(user);
            systemFieldService.addSystemField(sf);


            request.getWorkExperience().forEach(weReq -> {
                WorkExperience we = WorkExperienceMapper.workExperienceRequestIntoWorkExperience(weReq);
                we.setUser(user);
                workExperienceService.addWorkExperience(we);
            });


            request.getEducationDetails().forEach(edReq -> {
                EducationDetail ed = EducationDetailMapper.educationDetailRequestIntoEducationDetail(edReq);
                ed.setUser(user);
                educationDetailService.addEducationalDetail(ed);
            });


            request.getDependentDetails().forEach(ddReq -> {
                DependentDetail dd = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(ddReq);
                dd.setUser(user);
                dependentDetailService.addDependentDetails(dd);
            });

            request.getRelatedForms().forEach(rfReq -> {
                RelatedForm rf = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rfReq);
                rf.setUser(user);
                relatedFormService.addRelatedForm(rf);
            });


        return Message.USER + Message.TAB + Message.REGISTERED
                + Message.TAB + Message.SUCCESSFULLY + Message.DOT;
    }

    @Override
    public EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request) {
        User user = userService.findById(id);
        request.validate();

        WorkInfo workInfo = WorkInfoMapper.workInfoRequestIntoWorkInfo(request.getWorkInfo());
        workInfo.setUser(user);
        user.setWorkInfo(workInfo);

        PersonalDetail personalDetail = PersonalDetailMapper.personalDetailRequestIntoPersonalDetail(request.getPersonalDetail());
        personalDetail.setUser(user);
        user.setPersonalDetail(personalDetail);

        IdentityInfo identityInfo = IdentityInfoMapper.identityInfoRequestIntoIdentityInfo(request.getIdentityInfo());
        identityInfo.setUser(user);
        user.setIdentityInfo(identityInfo);

        ContactDetail contactDetail = ContactDetailMapper.contactDetailRequestIntoContactDetail(request.getContactDetail());
        contactDetail.setUser(user);
        user.setContactDetail(contactDetail);

        SystemField systemField = SystemFieldMapper.systemFieldRequestIntoSystemField(request.getSystemField());
        systemField.setUser(user);
        user.setSystemField(systemField);

        List<HierarchyInfo> hierarchyInfos = request.getHierarchyInfos().stream()
                .map(hi -> {
                    HierarchyInfo hierarchyInfo = HierarchyInfoMapper.hierarchyInfoRequestIntoHierarchyInfo(hi);
                    hierarchyInfo.setUser(user);
                    return hierarchyInfo;
                }).toList();
        user.getHierarchyInfos().clear();
        user.getHierarchyInfos().addAll(hierarchyInfos);

        List<WorkExperience> workExperiences = request.getWorkExperience().stream()
                .map(we -> {
                    WorkExperience workExperience = WorkExperienceMapper.workExperienceRequestIntoWorkExperience(we);
                    workExperience.setUser(user);
                    return workExperience;
                }).toList();
        user.getWorkExperiences().clear();
        user.getWorkExperiences().addAll(workExperiences);

        List<EducationDetail> educationDetails = request.getEducationDetails().stream()
                .map(ed -> {
                    EducationDetail educationDetail = EducationDetailMapper.educationDetailRequestIntoEducationDetail(ed);
                    educationDetail.setUser(user);
                    return educationDetail;
                }).toList();
        user.getEducationDetails().clear();
        user.getEducationDetails().addAll(educationDetails);

        List<DependentDetail> dependentDetails = request.getDependentDetails().stream()
                .map(dd -> {
                    DependentDetail dependentDetail = DependentDetailMapper.dependentDetailRequestIntoDependentDetail(dd);
                    dependentDetail.setUser(user);
                    return dependentDetail;
                }).toList();
        user.getDependentDetails().clear();
        user.getDependentDetails().addAll(dependentDetails);

        // Related Forms
        List<RelatedForm> relatedForms = request.getRelatedForms().stream()
                .map(rf -> {
                    RelatedForm relatedForm = RelatedFormMapper.relatedFormRequestIntoRelatedForm(rf);
                    relatedForm.setUser(user);
                    return relatedForm;
                }).toList();
        user.getRelatedForms().clear();
        user.getRelatedForms().addAll(relatedForms);

        userService.add(user);
        return EmployeeDetailMapper.toResponse(user);
    }

    @Override
    public EmployeeDetailsResponse getById(String id) {
        User user = userService.getById(id);
        return EmployeeDetailMapper.toResponse(user);
    }
}
