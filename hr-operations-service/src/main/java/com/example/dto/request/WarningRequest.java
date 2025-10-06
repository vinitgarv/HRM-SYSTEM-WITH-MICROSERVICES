package com.example.dto.request;


import com.example.exception.InvalidException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class WarningRequest {

    private String userId;
    private String subject;
    private LocalDate warningDate;
    private String description;

    public void validate()
    {
        if (userId == null || userId.isEmpty())
        {
            throw new InvalidException("Invalid userId");
        }

        if (subject == null || subject.isEmpty())
        {
            throw new InvalidException("Invalid subject");
        }

        if (warningDate == null || warningDate.isAfter(LocalDate.now()))
        {
            throw new InvalidException("Invalid warningDate (must be today or in the past)");
        }

        if (description == null || description.isEmpty())
        {
            throw new InvalidException("Invalid description");
        }
    }
}
