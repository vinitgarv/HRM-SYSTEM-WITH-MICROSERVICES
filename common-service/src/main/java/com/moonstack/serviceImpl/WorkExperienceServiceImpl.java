package com.moonstack.serviceImpl;

import com.moonstack.entity.WorkExperience;
import com.moonstack.repository.WorkExperienceRepository;
import com.moonstack.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    @Autowired
    private WorkExperienceRepository repository;

    @Override
    public WorkExperience addWorkExperience(WorkExperience workExperience) {
       return repository.save(workExperience);
    }
}
