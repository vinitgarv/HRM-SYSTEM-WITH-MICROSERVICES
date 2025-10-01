package com.moonstack.mapper;

import com.moonstack.dtos.request.ContactDetailRequest;
import com.moonstack.dtos.response.ContactDetailResponse;
import com.moonstack.entity.ContactDetail;
import com.moonstack.utils.Helper;

public class ContactDetailMapper
{
    public static ContactDetail contactDetailRequestIntoContactDetail(ContactDetailRequest request)
    {
        return ContactDetail.builder()
                .id(Helper.generateId())
                .workPhoneNumber(request.getWorkPhoneNumber())
                .personalMobile(request.getPersonalMobile())
                .extension(request.getExtension())
                .personalEmail(request.getPersonalEmail())
                .seatingLocation(request.getSeatingLocation())
                .tag(request.getTag())
                .presentAddress(request.getPresentAddress())
                .permanentAddress(request.getPermanentAddress())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static void updateFromRequest(ContactDetailRequest request, ContactDetail contactDetail) {
        contactDetail.setWorkPhoneNumber(request.getWorkPhoneNumber());
        contactDetail.setPersonalMobile(request.getPersonalMobile());
        contactDetail.setExtension(request.getExtension());
        contactDetail.setPersonalEmail(request.getPersonalEmail());
        contactDetail.setSeatingLocation(request.getSeatingLocation());
        contactDetail.setTag(request.getTag());
        contactDetail.setPresentAddress(request.getPresentAddress());
        contactDetail.setPermanentAddress(request.getPermanentAddress());
    }


    public static ContactDetailResponse contactDetailIntoContactDetailResponse(ContactDetail contactDetail)
    {
        return ContactDetailResponse.builder()
                .workPhoneNumber(contactDetail.getWorkPhoneNumber())
                .personalMobile(contactDetail.getPersonalMobile())
                .extension(contactDetail.getExtension())
                .personalEmail(contactDetail.getPersonalEmail())
                .seatingLocation(contactDetail.getSeatingLocation())
                .tag(contactDetail.getTag())
                .presentAddress(contactDetail.getPresentAddress())
                .permanentAddress(contactDetail.getPermanentAddress())
                .build();
    }
}
