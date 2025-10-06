package com.example.repository;

import com.example.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

public interface TimeSheetRepository extends JpaRepository<TimeSheet,String>, JpaSpecificationExecutor<TimeSheet>
{
        Boolean existsByDatePostedAndUser(LocalDate date, String user);


        Optional<TimeSheet> findByDatePostedAndUser(LocalDate date,String user);
}
