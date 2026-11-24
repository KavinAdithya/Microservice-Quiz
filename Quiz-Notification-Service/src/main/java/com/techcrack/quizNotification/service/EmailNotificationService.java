package com.techcrack.quizNotification.service;

import com.techcrack.quizNotification.config.NotificationConfiguration;
import com.techcrack.quizNotification.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmailNotificationService implements NotificationService {
    private JavaMailSender mailSender;
    private NotificationConfiguration notifyConfig;

    @Override
    public void sendNotification(Map<String, String> data) {
        boolean isValidData = validateData(data);

        if (!isValidData)
            throw new RuntimeException("Invalid parameters for Email Notification");

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(data.get("to"));
            message.setSubject(data.get("subject"));
            message.setText(data.get("body"));

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("EMAIL NOTIFICATION FAILED due to " + e.getMessage());
        }
    }

    public boolean validateData(Map<String, String> data) {
        return data.containsKey("to") && data.containsKey("subject") && data.containsKey("body");
    }
}
