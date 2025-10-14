package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest
{
    String newPassword;
    String confirmPassword;

    public void validate()
    {
        if ((newPassword == null || newPassword.isEmpty()) ||
                (confirmPassword == null || confirmPassword.isEmpty()))
            throw new RequestFailedException();

        validate(newPassword,confirmPassword);
    }

    public void validate(String newPassword, String confirmPassword)
    {
        if (newPassword.length() < 3)
            throw new RequestFailedException(Message.NEW_PASSWORD+Message.TAB+Message.IS+Message.TAB+
                    Message.TOO+Message.TAB+Message.SHORT+Message.DOT);

        if (confirmPassword.length() < 3)
            throw new RequestFailedException(Message.CONFIRM_PASSWORD+Message.TAB+Message.IS+Message.TAB+
                    Message.TOO+Message.TAB+Message.SHORT+Message.DOT);
    }
}
