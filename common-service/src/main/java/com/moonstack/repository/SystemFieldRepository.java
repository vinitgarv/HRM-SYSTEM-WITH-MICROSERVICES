package com.moonstack.repository;

import com.moonstack.entity.SystemField;
import com.moonstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemFieldRepository extends JpaRepository<SystemField,String> {
    void deleteByUser(User user);

}
