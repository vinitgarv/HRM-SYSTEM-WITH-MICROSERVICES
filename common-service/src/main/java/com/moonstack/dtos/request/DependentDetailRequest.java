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
public class DependentDetailRequest
{

    private String name;
    private String relation;
    private LocalDate dob;

    public void validate()
    {
        validateName(name, Message.NAME);
        validateRelation(relation, Message.RELATION);
        validateDob(dob, Message.DOB);
    }

    private void validateName(String name, String fieldName)
    {
        if (name == null || name.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (name.length() < 2) {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }
    }

    private void validateRelation(String relation, String fieldName)
    {
        if (relation == null || relation.isEmpty())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (relation.length() < 3)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }
    }

    private void validateDob(LocalDate dob, String fieldName)
    {
        if (dob == null)
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.EMPTY);
        }

        if (dob.isAfter(LocalDate.now()))
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.CANNOT + Message.TAB +
                    Message.BE + Message.TAB + Message.FUTURE + Message.DOT);
        }
    }
}
