package com.moonstack.service;

import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;

public interface IdentityInfoService {
    IdentityInfo addIdentityInfo(IdentityInfo identityInfo);
    void delete(User user);

}
