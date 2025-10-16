package com.moonstack.service;

import com.moonstack.dtos.request.EmailRequest;

public interface EmailService
{
    String sendEmail(EmailRequest emailRequest);
}
