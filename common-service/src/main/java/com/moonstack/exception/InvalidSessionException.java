package com.moonstack.exception;

public class InvalidSessionException extends RuntimeException {
    private final String data;

    public InvalidSessionException(String message, String data) {
        super(message);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}