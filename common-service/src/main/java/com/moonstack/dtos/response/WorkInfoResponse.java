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
public class WorkInfoResponse
{
    private String department;
    private String role;
    private String location;
    private String empType;
    private String designation;
    private String empStatus;
    private String sourceOfHire;
    private LocalDate dateOfJoining;
}
