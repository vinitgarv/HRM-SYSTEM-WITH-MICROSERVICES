package com.moonstack.service;

import com.moonstack.dto.request.EmployeeSalaryRequest;
import com.moonstack.dto.response.EmployeeSalaryResponse;

public interface EmployeeSalaryService
{
   EmployeeSalaryResponse add(EmployeeSalaryRequest request, String userId);

   EmployeeSalaryResponse update(EmployeeSalaryRequest request,String salaryId);

   EmployeeSalaryResponse getById(String userId);
}
