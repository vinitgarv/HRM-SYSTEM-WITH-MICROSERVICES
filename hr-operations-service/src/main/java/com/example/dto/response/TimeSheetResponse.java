package com.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TimeSheetResponse
{
   private String id;
   private String employee;
   private LocalDate date;
   private Double hours;
   private String description;
   private String approvedBy;
   private LocalDate approvalDate;
   private String status;
}
