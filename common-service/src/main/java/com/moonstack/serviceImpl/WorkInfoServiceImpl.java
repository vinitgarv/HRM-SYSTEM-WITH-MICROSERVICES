package com.moonstack.serviceImpl;

import com.moonstack.entity.User;
import com.moonstack.entity.WorkInfo;
import com.moonstack.repository.WorkInfoRepository;
import com.moonstack.service.WorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkInfoServiceImpl implements WorkInfoService {
    @Autowired
    private WorkInfoRepository repository;

    @Override
    public WorkInfo addWorkInfo(WorkInfo workInfo) {
       return repository.save(workInfo);
    }

    @Override
    public void delete(User user) {
        repository.deleteByUser(user);
    }
}
