package com.example.repository;

import com.example.entity.HolidayCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HolidayCalendarRepository extends JpaRepository<HolidayCalendar,String>
{
    Optional<HolidayCalendar> findByDate(LocalDate date);
}
