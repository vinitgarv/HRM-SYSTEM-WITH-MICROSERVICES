package com.moonstack.serviceImpl;

import com.moonstack.entity.PersonalDetail;
import com.moonstack.entity.User;
import com.moonstack.repository.PersonalDetailRepository;
import com.moonstack.service.PersonalDetailService;
import com.moonstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDetailServiceImpl implements PersonalDetailService {
    @Autowired
    private PersonalDetailRepository repository;

    @Autowired
    private UserService userService;


    @Override
    public PersonalDetail addPersonalDetail(PersonalDetail personalDetail) {
       return repository.save(personalDetail);
    }

    @Override
    public void delete(User user) {
       repository.deleteByUser(user);
    }
}
