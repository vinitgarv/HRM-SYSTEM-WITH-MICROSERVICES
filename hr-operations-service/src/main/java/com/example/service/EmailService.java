package com.example.service;

import com.example.dto.request.EmailRequest;

public interface EmailService
{
    String sendEmail(EmailRequest emailRequest);
}
