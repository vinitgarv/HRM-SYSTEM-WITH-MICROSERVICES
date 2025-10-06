package com.example.dto.request;

import com.example.exception.InvalidException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HolidayCalendarRequest
{
    private LocalDate date;
    private String remark;
    private String type;

    public void validate()
    {
        if ((date == null) || (remark.isEmpty() || remark == null) || (type.isEmpty() || type == null))
        {
            throw  new InvalidException("Invalid data");
        }
    }
}
