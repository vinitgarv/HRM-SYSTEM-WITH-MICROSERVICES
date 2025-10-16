package com.moonstack.service;

import com.moonstack.dtos.request.MessageRequest;
import com.moonstack.dtos.response.MessageResponse;

import java.util.List;

public interface MessageService
{
    List<MessageResponse> addMsg(List<MessageRequest> requests);

    List<MessageResponse> filter(String value);

    void refreshMessages();
}
