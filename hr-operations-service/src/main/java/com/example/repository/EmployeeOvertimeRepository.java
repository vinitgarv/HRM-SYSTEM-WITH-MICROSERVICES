package com.example.repository;

import com.example.entity.EmployeeOvertime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeOvertimeRepository extends JpaRepository<EmployeeOvertime,String> {
    Optional<EmployeeOvertime> findByUserAndDate(String user, LocalDate date);
}
