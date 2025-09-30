package com.moonstack.repository;

import com.moonstack.entity.PersonalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDetailRepository extends JpaRepository<PersonalDetail,String> {
}
