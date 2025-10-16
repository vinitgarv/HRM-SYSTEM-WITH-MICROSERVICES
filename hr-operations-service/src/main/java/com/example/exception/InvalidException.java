package com.example.exception;

public class InvalidException extends RuntimeException
{
    public InvalidException(String message)
    {
        super(message);
    }
}
