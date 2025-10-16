package com.moonstack.service;

import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.entity.EducationDetail;
import org.springframework.web.multipart.MultipartFile;

public interface EducationDetailService {
    EducationDetail addEducationalDetail(EducationDetail educationDetail);

}
