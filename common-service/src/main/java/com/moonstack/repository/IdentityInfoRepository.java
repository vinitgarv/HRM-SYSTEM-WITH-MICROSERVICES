package com.moonstack.repository;

import com.moonstack.entity.IdentityInfo;
import com.moonstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityInfoRepository extends JpaRepository<IdentityInfo,String> {
    void deleteByUser(User user);

}
