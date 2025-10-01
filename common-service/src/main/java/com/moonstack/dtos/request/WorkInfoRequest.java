package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkInfoRequest
{

    private String department;
    private String role;
    private String location;
    private String empType;
    private String designation;
    private String empStatus;
    private String sourceOfHire;
    private LocalDate dateOfJoining;

    public void validate()
    {
        validateField(department, 2, 50, Message.DEPARTMENT);
        validateField(role, 2, 50, Message.ROLE);
        validateField(location, 2, 50, Message.LOCATION);
        validateField(empType, 2, 50, Message.EMP_TYPE);
        validateField(designation, 2, 50, Message.DESIGNATION);
        validateField(empStatus, 2, 50, Message.EMP_STATUS);
        validateField(sourceOfHire, 2, 50, Message.SOURCE_OF_HIRE);
        validateDate(dateOfJoining, Message.DATE_OF_JOINING);
    }

    private void validateField(String field, int minLength, int maxLength, String fieldName)
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

        if (field.length() > maxLength)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.LONG + Message.DOT);
        }
    }

    private void validateDate(LocalDate date, String fieldName)
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
