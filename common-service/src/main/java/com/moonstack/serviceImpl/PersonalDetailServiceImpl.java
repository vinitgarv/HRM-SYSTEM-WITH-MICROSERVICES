package com.moonstack.serviceImpl;

import com.moonstack.entity.PersonalDetail;
import com.moonstack.repository.PersonalDetailRepository;
import com.moonstack.service.PersonalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDetailServiceImpl implements PersonalDetailService {
    @Autowired
    private PersonalDetailRepository repository;

    @Override
    public PersonalDetail addPersonalDetail(PersonalDetail personalDetail) {
       return repository.save(personalDetail);
    }
}
