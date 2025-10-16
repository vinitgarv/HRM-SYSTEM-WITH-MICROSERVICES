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
public class SystemFieldRequest {

    private String addedBy;
    private String modifiedBy;
    private LocalDate addedTime;
    private LocalDate modifiedTime;

    public void validate()
    {
        validateUser(addedBy, Message.ADDED_BY);
        validateUser(modifiedBy, Message.MODIFIED_BY);
        validateDate(addedTime, Message.ADDED_TIME);
        validateDate(modifiedTime, Message.MODIFIED_TIME);
    }

    private void validateUser(String user, String fieldName)
    {
        if (user == null || user.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (user.length() < 2 || user.length() > 50)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.OR + Message.TOO + Message.TAB + Message.LONG);
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
