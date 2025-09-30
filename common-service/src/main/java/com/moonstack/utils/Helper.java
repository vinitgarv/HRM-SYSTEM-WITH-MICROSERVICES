package com.moonstack.utils;

import com.moonstack.constants.Message;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class Helper
{
    public static String generateId()
    {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
    }

    public static String getBase64Encoded(String password)
    {
        return Base64.getEncoder()
                .encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    public static String getRandomPassword(String firstName,String lastName)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName)
                .append(Message.AT_SIGN)
                .append(lastName)
                .append(Message.RANDOM_NUM);
        return sb.toString();
    }

    public static String generateRandomPassword() {return UUID.randomUUID().toString().substring(0, 8);
    }
}
