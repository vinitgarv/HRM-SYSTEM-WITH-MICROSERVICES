package com.moonstack.repository;

import com.moonstack.entity.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,String>
{
    Boolean existsByUserId(String userId);

    Optional<EmployeeSalary> findByUserId(String userId);
}
