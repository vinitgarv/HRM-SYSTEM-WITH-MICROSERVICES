package com.moonstack.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceRequest
{
    private String companyName;
    private String jobTitle;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String jobDescription;
}
