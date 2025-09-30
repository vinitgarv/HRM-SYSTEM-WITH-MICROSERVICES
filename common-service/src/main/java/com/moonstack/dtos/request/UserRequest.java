package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

import java.util.regex.Pattern;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest
{
    private String firstName;
    private String lastName;
    private String email;

    public void validate()
    {
        if ((firstName == null || firstName.isEmpty()) ||
            (lastName == null || lastName.isEmpty())   ||
            (email == null || email.isEmpty())
           )
            throw  new RequestFailedException();

        validate(firstName,lastName,email);
    }

    public void validate(String firstName,String lastName,String email)
    {
        if (firstName.length() < 3)
            throw new RequestFailedException(Message.FIRSTNAME+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);

        if (lastName.length() < 3)
            throw new RequestFailedException(Message.LASTNAME+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        boolean isEmailValid = Pattern.matches(emailRegex, email);
        if (!isEmailValid)
            throw new RequestFailedException(Message.INVALID+Message.TAB+Message.EMAIL+Message.DOT);

    }
}
