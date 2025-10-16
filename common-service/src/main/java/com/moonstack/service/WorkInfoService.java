package com.moonstack.service;

import com.moonstack.entity.User;
import com.moonstack.entity.WorkInfo;

public interface WorkInfoService {
    WorkInfo addWorkInfo(WorkInfo workInfo);
    void delete(User user);
}
