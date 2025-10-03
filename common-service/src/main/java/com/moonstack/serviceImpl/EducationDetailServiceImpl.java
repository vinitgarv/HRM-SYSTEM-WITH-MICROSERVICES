package com.moonstack.serviceImpl;

import com.moonstack.entity.EducationDetail;
import com.moonstack.repository.EducationDetailRepository;
import com.moonstack.service.EducationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationDetailServiceImpl implements EducationDetailService {

    @Autowired
    private EducationDetailRepository repository;

    @Override
    public EducationDetail addEducationalDetail(EducationDetail educationDetail) {
       return repository.save(educationDetail);
    }
}
