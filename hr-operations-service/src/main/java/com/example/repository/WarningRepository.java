package com.example.repository;

import com.example.entity.Warning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WarningRepository extends JpaRepository<Warning,String>
{
    Optional<Warning> findBySubjectAndWarningDateAndUser(String subject, LocalDate date, String user);
}
