package com.moonstack.exception;

public class ForbiddenException extends RuntimeException
{
    private final String data;

    public ForbiddenException(String message, String data) {
        super(message);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
