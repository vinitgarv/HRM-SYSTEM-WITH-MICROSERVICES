package com.example.exception;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException(String msg) {
        super(msg);
    }
}