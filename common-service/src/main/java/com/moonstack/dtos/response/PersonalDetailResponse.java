package com.moonstack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDetailResponse
{
    private LocalDate dob;
    private String maritalStatus;
    private String aboutMe;
    private String expertise;
}
