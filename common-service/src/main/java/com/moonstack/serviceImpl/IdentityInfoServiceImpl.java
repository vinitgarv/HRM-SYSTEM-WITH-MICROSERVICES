package com.moonstack.serviceImpl;

import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;
import com.moonstack.repository.IdentityInfoRepository;
import com.moonstack.service.IdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
