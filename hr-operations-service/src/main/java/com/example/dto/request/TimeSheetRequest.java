package com.example.dto.request;


import com.example.exception.InvalidException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TimeSheetRequest
{
    private String userId;
    private LocalDate datePosted;
    private Double hours;
    private String description;

    public void validate()
    {
        if (userId == null || userId.isEmpty())
        {
            throw new InvalidException("Invalid userId");
        }

        if (datePosted == null || datePosted.isAfter(LocalDate.now()))
        {
            throw new InvalidException("Invalid date (must be today or in the past)");
        }

        if (hours == null || (hours < 1 && hours > 24))
        {
            throw new InvalidException("Invalid hours");
        }

        if (description.isEmpty() || description == null || description.length() < 10)
        {
            throw new InvalidException("Invalid description");
        }
    }
}
