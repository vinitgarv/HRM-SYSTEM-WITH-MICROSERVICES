package com.moonstack.service;

import com.moonstack.dtos.request.EmployeeDetailsRequest;
import com.moonstack.dtos.response.EmployeeDetailsResponse;
import com.moonstack.dtos.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeDetailsService {

    String addEmployeeDetails(EmployeeDetailsRequest request, String userId);

    EmployeeDetailsResponse update(String id, EmployeeDetailsRequest request);

    EmployeeDetailsResponse getById(String id);

    FileUploadResponse uploadDocument(MultipartFile file, String employeeId, String type);

    String deleteDocument(String employeeId, String fileName);
}
