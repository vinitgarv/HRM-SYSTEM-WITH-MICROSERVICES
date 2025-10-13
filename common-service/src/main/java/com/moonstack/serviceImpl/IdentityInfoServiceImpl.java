package com.moonstack.serviceImpl;

import com.moonstack.dtos.request.RejectDocumentRequest;
import com.moonstack.dtos.response.FileUploadResponse;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;
import com.moonstack.exception.NotFoundException;
import com.moonstack.repository.IdentityInfoRepository;
import com.moonstack.service.IdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Service
public class IdentityInfoServiceImpl implements IdentityInfoService {
    @Autowired
    private IdentityInfoRepository repository;

    @Override
    public IdentityInfo addIdentityInfo(IdentityInfo identityInfo) {
        return repository.save(identityInfo);
    }

    @Override
    public void delete(User user) {
        repository.deleteByUser(user);
    }


    @Override
    public FileUploadResponse uploadIdentityInfoFile(MultipartFile file, String employeeId, String type) {
        try {
            // 1. Create employee folder if not exists
            Path employeeFolder = Paths.get("uploads/employee_" + employeeId+"/identity_info");
            if (!Files.exists(employeeFolder)) {
                Files.createDirectories(employeeFolder);
            }

            // 2. Get original file info
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) throw new RuntimeException("File must have a name");

            // 3. Extract extension
            String fileType = "";
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex > 0) {
                fileType = originalFilename.substring(dotIndex + 1);
            }

            // 4. Create new filename: filename_timestamp_userId_type
            String baseName = originalFilename.substring(0, dotIndex);
            long timestamp = System.currentTimeMillis();
            String newFilename = baseName + "_" + timestamp + "_" + employeeId + "_" + type+"."+ fileType ;

            // 5. Save file
            Path filePath = employeeFolder.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String filenameWithoutExtension = newFilename.substring(0, newFilename.lastIndexOf('.'));
            return FileUploadResponse.builder()
                    .fileName(filenameWithoutExtension)
                    .fileType(fileType)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }


    @Override
    public IdentityInfo findIdentityInfoById(String id) {
        return   repository.findById(id).orElseThrow(() -> new NotFoundException("Identity Info Not Found"));
    }

    @Override
    public String verifyDocument(String documentId) {
        IdentityInfo identityInfo = findIdentityInfoById(documentId);

        identityInfo.setStatus("Verified");
        identityInfo.setVerified(true);
        identityInfo.setVerifiedBy("ADMIN");
        identityInfo.setVerificationDate(LocalDate.now());
        identityInfo.setRemarks("Details Matched");

        repository.save(identityInfo);
        return identityInfo.getDocumentType()+" Verified Successfully ";
    }

    @Override
    public String rejectDocument(String documentId, RejectDocumentRequest request) {
        IdentityInfo identityInfo = findIdentityInfoById(documentId);

        if(identityInfo.getStatus().equals("Verified")) {
            return "Document Already Verified  can't reject ";
        }

        identityInfo.setStatus("Rejected");
        identityInfo.setVerifiedBy("ADMIN");
        identityInfo.setVerified(false);
        identityInfo.setVerificationDate(LocalDate.now());
        identityInfo.setRemarks(request.getRemark());

        repository.save(identityInfo);
        return identityInfo.getDocumentType()+" Rejected Successfully ";
    }

}
