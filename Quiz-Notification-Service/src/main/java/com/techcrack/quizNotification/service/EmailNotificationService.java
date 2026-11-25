package com.techcrack.quizNotification.service;

import com.techcrack.quizNotification.config.NotificationConfiguration;
import com.techcrack.quizNotification.model.EmailNotificationModel;
import com.techcrack.quizNotification.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailNotificationService implements NotificationService<EmailNotificationModel> {
    private JavaMailSender mailSender;
    private NotificationConfiguration notifyConfig;

    @Override
    public void sendNotification(EmailNotificationModel data) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(data.to());
            message.setSubject(data.subject());
            message.setText(data.body());

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("EMAIL NOTIFICATION FAILED due to " + e.getMessage());
        }
    }
}
