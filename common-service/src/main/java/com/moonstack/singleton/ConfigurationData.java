package com.moonstack.singleton;

import com.moonstack.dtos.response.MessageResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ConfigurationData {
    private Map<String, MessageResponse> errorMsgs;
    private Map<String, MessageResponse> infoMsgs;
    private static volatile ConfigurationData INSTANCE;

    private ConfigurationData()
    {
        this.errorMsgs = new HashMap<>();
        this.infoMsgs = new HashMap<>();
    }

    public  static ConfigurationData getInstance()
    {
        ConfigurationData cd = INSTANCE;

        if (cd == null)
        synchronized (ConfigurationData.class)
        {
            cd = INSTANCE;
            if (cd == null)
            {
                cd = new ConfigurationData();
                INSTANCE = cd;
            }
        }
        return cd;
    }
}
