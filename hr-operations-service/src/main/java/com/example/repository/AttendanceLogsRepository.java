package com.example.repository;

import com.example.entity.AttendanceLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceLogsRepository extends JpaRepository<AttendanceLogs,String>
{
        List<AttendanceLogs> findByDateAndUser(LocalDate date, String user);

        @Query("SELECT a FROM AttendanceLogs a WHERE MONTH(a.date) = :month AND YEAR(a.date) = :year AND a.user = :user")
        List<AttendanceLogs> findByMonthAndUser(@Param("month") int month,
                                                @Param("year") int year,
                                                @Param("user") String user);

        List<AttendanceLogs> findByUserAndDate(String user, LocalDate today);
}
