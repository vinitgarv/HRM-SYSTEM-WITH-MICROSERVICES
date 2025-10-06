package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE holiday_calendar SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class HolidayCalendar extends AbstractPersistable
{
    private LocalDate date;
    private String day;
    private String remark;
    private String type;
}
