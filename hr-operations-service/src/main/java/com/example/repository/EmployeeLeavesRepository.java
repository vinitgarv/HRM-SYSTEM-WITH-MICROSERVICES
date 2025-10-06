package com.example.repository;

import com.example.entity.EmployeeLeaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeLeavesRepository extends JpaRepository<EmployeeLeaves,String> {
    @Query("SELECT e FROM EmployeeLeaves e " +
            "WHERE e.user = :user " +
            "AND e.isDeleted = false " +
            "AND ( (e.startDate <= :endDate) AND (e.endDate >= :startDate) )")
    List<EmployeeLeaves> findOverlappingLeaves(
            @Param("user") String user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
