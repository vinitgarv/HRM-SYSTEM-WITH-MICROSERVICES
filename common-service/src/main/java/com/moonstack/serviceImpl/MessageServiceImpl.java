package com.moonstack.serviceImpl;

import com.moonstack.dtos.request.MessageRequest;
import com.moonstack.dtos.response.MessageResponse;
import com.moonstack.entity.Message;
import com.moonstack.mapper.MessageMapper;
import com.moonstack.repository.MessageRepository;
import com.moonstack.service.MessageService;
import com.moonstack.singleton.ConfigurationData;
import com.moonstack.utils.SpecificationFilters.MessageFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService
{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<MessageResponse> addMsg(List<MessageRequest> requests)
    {


        List<Message> messages = requests.stream()
                                .map(MessageMapper::convertMessageRequestToMessage)
                                .toList();

        messageRepository.saveAll(messages);
        return messages.stream().map(MessageMapper::convertMessageToMessageResponse).toList();
    }

    @Override
    public List<MessageResponse> filter(String value)
    {
        Specification<Message> specification = Specification.where(MessageFilters.filter(value));

        List<Message> messages = messageRepository.findAll(specification);
        return messages.stream().map(MessageMapper::convertMessageToMessageResponse).toList();
    }

    @Override
    public void refreshMessages() {
        ConfigurationData configurationData = ConfigurationData.getInstance();

        configurationData.getErrorMsgs().clear();
        configurationData.getInfoMsgs().clear();

        messageRepository.findAll().forEach(message ->
        {
            MessageResponse response = new MessageResponse();
            response.setKey(message.getId());
            response.setCode(message.getCode());
            response.setValue(message.getValue());
            response.setType(message.getType());

            if ("ERROR".equalsIgnoreCase(message.getType()))
            {
                configurationData.getErrorMsgs().put(message.getId(), response);
            }
            else if ("INFO".equalsIgnoreCase(message.getType()))
            {
                configurationData.getInfoMsgs().put(message.getId(), response);
            }
        });

        System.out.println("Error Map : "+configurationData.getErrorMsgs());
        System.out.println("Info Map : "+configurationData.getInfoMsgs());
    }

}
