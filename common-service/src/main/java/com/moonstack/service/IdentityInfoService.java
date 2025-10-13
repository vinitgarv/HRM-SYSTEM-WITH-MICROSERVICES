package com.moonstack.service;

import com.moonstack.dtos.request.RejectDocumentRequest;
import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface IdentityInfoService {
    IdentityInfo addIdentityInfo(IdentityInfo identityInfo);

    void delete(User user);

    public FileUploadResponse uploadIdentityInfoFile(MultipartFile file, String employeeId, String type);

    String verifyDocument(String documentId);

    String rejectDocument(String documentId, RejectDocumentRequest request);

    public IdentityInfo findIdentityInfoById(String id);
}
