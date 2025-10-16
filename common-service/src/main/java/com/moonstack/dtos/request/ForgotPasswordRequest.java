package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

import java.util.regex.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest
{
    private String email;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate()
    {
        validateEmail(email,Message.EMAIL);
    }

    private void validateEmail(String email,String fieldName)
    {
        if (email == null || email.isEmpty())
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.REQUIRED+Message.DOT);
        }

        if (!EMAIL_PATTERN.matcher(email).matches())
        {
            throw new RequestFailedException(fieldName + Message.TAB + Message.IS + Message.TAB + Message.INVALID);
        }
    }
}
