package com.example.flightstatus.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public NotificationService() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @RabbitListener(queues = "flightStatusQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // Send email notification
        sendEmail("recipient@example.com", "Flight Status Update", message);
        // Send SMS notification
        sendSms("+1234567890", message);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private void sendSms(String to, String text) {
        Message.creator(new PhoneNumber(to), new PhoneNumber(twilioPhoneNumber), text).create();
    }
}
