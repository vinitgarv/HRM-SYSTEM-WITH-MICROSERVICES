package com.moonstack.dtos.request;

import com.moonstack.constants.Message;
import com.moonstack.enums.RolesEnum;
import com.moonstack.exception.RequestFailedException;
import lombok.*;

import java.util.Set;
import java.util.regex.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    public void validate()
    {
        validateFirstName(firstName, Message.FIRSTNAME);
        validateLastName(lastName,Message.LASTNAME);
        validateEmail(email,Message.EMAIL);
        validateRoles(roles,Message.ROLE);
    }

    private void validateFirstName(String firstName,String fieldName)
    {
        if (firstName == null || firstName.isEmpty())
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.REQUIRED+Message.DOT);
        }

        if (firstName.length() < 3)
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);
        }
    }

    private void validateLastName(String lastName,String fieldName)
    {
        if (lastName == null || lastName.isEmpty())
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.REQUIRED+Message.DOT);
        }

        if (lastName.length() < 3)
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.TOO+Message.TAB+Message.SHORT+Message.DOT);
        }
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

    private void validateRoles(Set<String> roles,String fieldName)
    {
        if(roles.size() > 1)
        {
            throw new RequestFailedException(Message.MULTIPLE+Message.TAB+Message.ROLES+Message.TAB+Message.NOT+Message.TAB+Message.ALLOWED+Message.DOT);
        }

        if (roles.isEmpty())
        {
            throw new RequestFailedException(fieldName+Message.TAB+Message.IS+Message.TAB+Message.REQUIRED+Message.DOT);
        }

        roles.forEach(role -> RolesEnum.validate(role));
    }
}