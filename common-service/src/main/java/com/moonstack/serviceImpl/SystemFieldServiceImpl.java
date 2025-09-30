package com.moonstack.serviceImpl;

import com.moonstack.entity.SystemField;
import com.moonstack.repository.SystemFieldRepository;
import com.moonstack.service.SystemFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemFieldServiceImpl implements SystemFieldService {

    @Autowired
    private SystemFieldRepository repository;

    @Override
    public SystemField addSystemField(SystemField systemField) {
      return   repository.save(systemField);
    }
}
