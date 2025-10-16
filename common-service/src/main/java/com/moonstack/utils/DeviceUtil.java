package com.moonstack.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceUtil
{
    public static String generateDeviceId(HttpServletRequest request)
    {
        try
        {
            String ip = getClientIp(request);
            String userAgent = request.getHeader("User-Agent");

           // String rawData = userAgent;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(userAgent.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash)
            {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error generating Device ID", e);
        }
    }

    public static String getDeviceName(HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) return "Unknown Device";

        String os = getOS(userAgent);
        String browser = getBrowser(userAgent);
        return os + " / " + browser;
    }

    private static String getClientIp(HttpServletRequest request)
    {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null)
        {
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    private static String getOS(String userAgent)
    {
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "Mac";
        if (userAgent.contains("X11")) return "Unix";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iPhone")) return "iPhone";
        return "Unknown OS";
    }

    private static String getBrowser(String userAgent)
    {
        if (userAgent.contains("Edge")) return "Edge";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) return "Safari";
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) return "Internet Explorer";
        return "Unknown Browser";
    }
}
