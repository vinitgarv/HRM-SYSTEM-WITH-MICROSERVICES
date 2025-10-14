package com.moonstack.exception;


import com.example.constants.Message;

public class RequestFailedException extends RuntimeException
{
    public RequestFailedException(String msg)
    {
        super(msg);
    }

    public RequestFailedException()
    {
        super(Message.REQUEST+Message.TAB+Message.FAILED+Message.DOT);
    }
}
