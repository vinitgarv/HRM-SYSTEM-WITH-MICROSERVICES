package com.moonstack.mapper;

import com.moonstack.dto.request.EmployeeSalaryRequest;
import com.moonstack.dto.response.EmployeeSalaryResponse;
import com.moonstack.entity.EmployeeSalary;
import com.moonstack.util.UtilsMethods;

public class EmployeeSalaryMapper
{
    public static EmployeeSalary convertEmployeeSalaryRequestToEmployeeSalary(EmployeeSalaryRequest request)
    {
        Double netSal = request.getBaseSalary() + request.getAllowances()
                - request.getDeductions() - request.getTaxAmounts();

        return EmployeeSalary.builder()
                .id(UtilsMethods.generateId())
                .isActive(true)
                .isDeleted(false)
                .baseSalary(request.getBaseSalary())
                .allowances(request.getAllowances())
                .deductions(request.getDeductions())
                .taxAmounts(request.getTaxAmounts())
                .bankName(request.getBankName())
                .accountNumber(request.getAccountNumber())
                .netSalary(netSal)
                .build();
    }

    public static EmployeeSalaryResponse convertEmployeeSalaryToEmployeeSalaryResponse(EmployeeSalary employeeSalary)
    {
        return EmployeeSalaryResponse.builder()
                .userId(employeeSalary.getUserId())
                .baseSalary(employeeSalary.getBaseSalary())
                .allowances(employeeSalary.getAllowances())
                .deductions(employeeSalary.getDeductions())
                .taxAmounts(employeeSalary.getTaxAmounts())
                .netSalary(employeeSalary.getNetSalary())
                .bankName(employeeSalary.getBankName())
                .accountNumber(employeeSalary.getAccountNumber())
                .build();
    }
}
