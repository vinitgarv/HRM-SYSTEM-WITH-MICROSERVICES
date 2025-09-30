package com.moonstack.repository;

import com.moonstack.entity.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailRepository extends JpaRepository<ContactDetail,String> {
}
