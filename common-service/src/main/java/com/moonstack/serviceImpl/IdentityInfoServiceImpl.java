package com.moonstack.serviceImpl;

import com.moonstack.dtos.request.RejectDocumentRequest;
import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;
import com.moonstack.exception.NotFoundException;
import com.moonstack.repository.IdentityInfoRepository;
import com.moonstack.service.IdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
