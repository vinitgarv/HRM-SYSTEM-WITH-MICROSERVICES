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
public class EducationDetailResponse
{
    private String instituteName;
    private String degreeOrDiploma;
    private String specialization;
    private LocalDate completionDate;
    private String fileName;
    private String fileType;
}
