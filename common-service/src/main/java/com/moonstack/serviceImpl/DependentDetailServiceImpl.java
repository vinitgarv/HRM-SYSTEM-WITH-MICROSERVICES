package com.moonstack.serviceImpl;

import com.moonstack.entity.DependentDetail;
import com.moonstack.repository.DependentDetailRepository;
import com.moonstack.service.DependentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DependentDetailServiceImpl implements DependentDetailService {

    @Autowired
    private DependentDetailRepository repository;

    @Override
    public DependentDetail addDependentDetails(DependentDetail dependentDetail) {
        return repository.save(dependentDetail);
    }
}
