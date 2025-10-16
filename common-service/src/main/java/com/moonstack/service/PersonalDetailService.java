package com.moonstack.service;

import com.moonstack.entity.PersonalDetail;
import com.moonstack.entity.User;

public interface PersonalDetailService {
    PersonalDetail addPersonalDetail(PersonalDetail personalDetail);
    void delete(User user);

}
