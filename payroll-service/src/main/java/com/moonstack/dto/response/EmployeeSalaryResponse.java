package com.moonstack.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSalaryResponse
{
    private String userId;
    private String username;
    private Double baseSalary;
    private Double allowances;
    private Double deductions;
    private Double taxAmounts;

    private Double netSalary;

    private String bankName;
    private String accountNumber;
}
