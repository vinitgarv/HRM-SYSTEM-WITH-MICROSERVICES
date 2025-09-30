package com.moonstack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailResponse
{
    private String workPhoneNumber;
    private String personalMobile;
    private String extension;
    private String personalEmail;
    private String seatingLocation;
    private String tag;
    private String presentAddress;
    private String permanentAddress;
}
