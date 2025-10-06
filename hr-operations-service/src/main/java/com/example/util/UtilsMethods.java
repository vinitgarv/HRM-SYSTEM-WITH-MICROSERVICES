package com.example.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class UtilsMethods {
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
    }


    public static String geTBase64Encoded(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateRandomPassword() {return UUID.randomUUID().toString().substring(0, 8);
    }
}
