package com.moonstack.repository;

import com.moonstack.entity.ContactDetail;
import com.moonstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailRepository extends JpaRepository<ContactDetail,String> {
    void deleteByUser(User user);
}
