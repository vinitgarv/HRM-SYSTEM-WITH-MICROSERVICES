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
public class SystemFieldRequest
{
    private String addedBy;
    private String modifiedBy;
    private LocalDate addedTime;
    private LocalDate modifiedTime;
}
