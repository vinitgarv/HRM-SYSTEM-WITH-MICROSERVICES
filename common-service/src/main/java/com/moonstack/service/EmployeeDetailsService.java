package com.moonstack.service;

import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;

public interface EmployeeDetailsService {

    String addEmployeeDetails(EmployeeDetailsRequest request,String userId);

    EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request);

    EmployeeDetailsResponse getById(String id);
}
