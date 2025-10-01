package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationDetailRequest {

    private String instituteName;
    private String degreeOrDiploma;
    private String specialization;
    private LocalDate completionDate;

    public void validate() {
        validateField(instituteName, 3, Message.INSTITUTE_NAME);
        validateField(degreeOrDiploma, 2, Message.DEGREE_OR_DIPLOMA);
        validateField(specialization, 2, Message.SPECIALIZATION);
        validateCompletionDate(completionDate, Message.COMPLETION_DATE);
    }

    private void validateField(String field, int minLength, String fieldName)
    {
        if (field == null || field.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (field.length() < minLength)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }
    }

    private void validateCompletionDate(LocalDate date, String fieldName)
    {
        if (date == null)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (date.isAfter(LocalDate.now()))
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.CANNOT + Message.TAB +
                    Message.BE + Message.TAB + Message.FUTURE + Message.DOT);
        }
    }
}
