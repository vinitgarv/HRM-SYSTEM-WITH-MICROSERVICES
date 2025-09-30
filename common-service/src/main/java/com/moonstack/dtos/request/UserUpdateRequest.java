package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest
{
    private String firstName;
    private String lastName;
    private String password;

    public void validate()
    {
        if ((firstName == null || firstName.isEmpty()) ||
            (lastName == null || lastName.isEmpty())   ||
            (password == null || password.isEmpty())
        )
            throw  new RequestFailedException();
        validate(firstName,lastName,password);
    }

    public void validate(String firstName,String lastName,String password)
    {
        if (firstName.length() < 3)
            throw new RequestFailedException(Message.FIRSTNAME+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);

        if (lastName.length() < 3)
            throw new RequestFailedException(Message.LASTNAME+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);

        if (password.length() < 3)
            throw new RequestFailedException(Message.PASSWORD+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);
    }
}
