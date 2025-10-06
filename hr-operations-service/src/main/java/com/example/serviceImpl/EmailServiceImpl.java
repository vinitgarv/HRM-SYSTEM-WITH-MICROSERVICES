package com.example.serviceImpl;

import com.example.dto.request.EmailRequest;
import com.example.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendEmail(EmailRequest emailRequest)
    {
        try
        {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getMessage(), true); // true = HTML support

            mailSender.send(mimeMessage);
            return "Email sent successfully to " + emailRequest.getTo();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            return "Error while sending email: " + e.getMessage();
        }
    }
}
