package com.example.repository;

import com.example.entity.AttendanceApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceApprovalRepository extends JpaRepository<AttendanceApproval, String> {
//    Optional<AttendanceApproval> findByUserAndDate(User user, LocalDate date);
    Optional<AttendanceApproval> findById(String requestId);
}
