package com.moonstack.dtos.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationDetailRequest {
    private String instituteName;
    private String degreeOrDiploma;
    private String specialization;
    private LocalDate completionDate;
}
