package com.moonstack.repository;

import com.moonstack.entity.WorkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkInfoRepository extends JpaRepository<WorkInfo,String> {
}