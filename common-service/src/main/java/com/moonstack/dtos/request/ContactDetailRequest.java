package com.moonstack.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailRequest {
    private String workPhoneNumber;
    private String personalMobile;
    private String extension;
    private String personalEmail;
    private String seatingLocation;
    private String tag;
    private String presentAddress;
    private String permanentAddress;
}
