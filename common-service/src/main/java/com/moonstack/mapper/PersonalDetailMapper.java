package com.moonstack.mapper;

import com.moonstack.dtos.request.PersonalDetailRequest;
import com.moonstack.dtos.response.PersonalDetailResponse;
import com.moonstack.entity.PersonalDetail;
import com.moonstack.utils.Helper;

public class PersonalDetailMapper {
    public static PersonalDetail personalDetailRequestIntoPersonalDetail(PersonalDetailRequest request) {
        return PersonalDetail.builder()
                .id(Helper.generateId())
                .dob(request.getDob())
                .maritalStatus(request.getMaritalStatus())
                .aboutMe(request.getAboutMe())
                .expertise(request.getExpertise())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static void updateFromRequest(PersonalDetailRequest request, PersonalDetail personalDetail) {
        personalDetail.setDob(request.getDob());
        personalDetail.setMaritalStatus(request.getMaritalStatus());
        personalDetail.setAboutMe(request.getAboutMe());
        personalDetail.setExpertise(request.getExpertise());
    }

    public static PersonalDetailResponse personalDetailIntoPersonalDetailResponse(PersonalDetail personalDetail) {
        return PersonalDetailResponse.builder()
                .dob(personalDetail.getDob())
                .maritalStatus(personalDetail.getMaritalStatus())
                .aboutMe(personalDetail.getAboutMe())
                .expertise(personalDetail.getExpertise())
                .build();
    }
}
