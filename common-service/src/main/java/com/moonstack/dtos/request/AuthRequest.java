package com.moonstack.dtos.request;

import com.moonstack.exception.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest
{
    private String email;
    private String password;

    public void validate(){
        boolean invalid = this.email == null || this.password == null || this.email.isEmpty() || this.password.isEmpty();
        if (invalid) throw new RequestFailedException("Missing Required param.");
    }
}
