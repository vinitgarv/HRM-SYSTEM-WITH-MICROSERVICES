package com.moonstack.repository;

import com.moonstack.entity.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,String> {
}
