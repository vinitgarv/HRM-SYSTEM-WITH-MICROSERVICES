package com.moonstack.exception;

public class ForbiddenException extends RuntimeException
{
    public ForbiddenException(String msg)
    {
        super(msg);
    }
}
