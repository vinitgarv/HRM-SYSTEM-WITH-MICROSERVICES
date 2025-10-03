package com.moonstack.runner;

import com.moonstack.dtos.response.MessageResponse;
import com.moonstack.repository.MessageRepository;
import com.moonstack.singleton.ConfigurationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationDataRunner implements CommandLineRunner
{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void run(String... args) throws Exception
    {
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
