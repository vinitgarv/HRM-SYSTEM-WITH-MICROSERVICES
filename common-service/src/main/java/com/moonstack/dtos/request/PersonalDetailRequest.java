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
public class PersonalDetailRequest
{

    private LocalDate dob;
    private String maritalStatus;
    private String aboutMe;
    private String expertise;

    public void validate()
    {
        // DOB must not be null or in the future
        if (dob == null || dob.isAfter(LocalDate.now()))
        {
            throw new RequestFailedException(Message.DOB + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        // Marital status must be valid
        if (maritalStatus == null || maritalStatus.isEmpty() ||
                !(maritalStatus.equalsIgnoreCase("SINGLE") ||
                        maritalStatus.equalsIgnoreCase("MARRIED") ||
                        maritalStatus.equalsIgnoreCase("DIVORCED") ||
                        maritalStatus.equalsIgnoreCase("WIDOWED")))
        {
            throw new RequestFailedException(Message.MARITAL_STATUS + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }

        // About me must not exceed 500 chars
        if (aboutMe != null && aboutMe.length() > 500)
        {
            throw new RequestFailedException(Message.ABOUT_ME + Message.TAB + Message.IS + Message.TAB + Message.TOO + Message.TAB + Message.LONG);
        }

        // Expertise must not be empty and within limit
        if (expertise == null || expertise.isEmpty() || expertise.length() > 200)
        {
            throw new RequestFailedException(Message.EXPERTISE + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }
    }
}
