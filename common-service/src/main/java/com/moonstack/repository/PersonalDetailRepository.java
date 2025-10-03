package com.moonstack.repository;

import com.moonstack.entity.PersonalDetail;
import com.moonstack.entity.User;
import com.moonstack.entity.WorkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalDetailRepository extends JpaRepository<PersonalDetail,String> {
    void deleteByUser(User user);

}
