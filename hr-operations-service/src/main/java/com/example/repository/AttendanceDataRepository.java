package com.example.repository;

import com.example.entity.AttendanceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceDataRepository  extends JpaRepository<AttendanceData,String> {
    Optional<AttendanceData> findByUserAndDate(String user, LocalDate date);

    @Query("SELECT a FROM AttendanceData a WHERE a.user = :user AND MONTH(a.date) = :month AND YEAR(a.date) = :year AND a.firstPunchInTime > :lateTime")
    List<AttendanceData> findLatePunchIns(@Param("user") String user,
                                          @Param("month") int month,
                                          @Param("year") int year,
                                          @Param("lateTime") LocalTime lateTime);

    @Query("SELECT a FROM AttendanceData a WHERE a.user = :user AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<AttendanceData> findByUserAndMonthAndYear(@Param("user") String user,
                                                   @Param("month") int month,
                                                   @Param("year") int year);
}
