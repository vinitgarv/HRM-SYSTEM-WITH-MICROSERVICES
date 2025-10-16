package com.moonstack.mapper;

import com.moonstack.dtos.request.MessageRequest;
import com.moonstack.dtos.response.MessageResponse;
import com.moonstack.entity.Message;
import com.moonstack.utils.Helper;

public class MessageMapper
{
    public static Message convertMessageRequestToMessage(MessageRequest request)
    {
        return Message.builder()
                .id(request.getKey())
                .code(request.getCode())
                .value(request.getValue())
                .type(request.getType())
                .isActive(true)
                .deleted(false)
                .build();
    }

    public static MessageResponse convertMessageToMessageResponse(Message message)
    {
        return MessageResponse.builder()
                .key(message.getId())
                .code(message.getCode())
                .value(message.getValue())
                .type(message.getType())
                .build();
    }

}
