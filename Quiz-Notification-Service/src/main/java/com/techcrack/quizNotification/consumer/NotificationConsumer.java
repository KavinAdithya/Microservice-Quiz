package com.techcrack.quizNotification.consumer;

import com.techcrack.quizNotification.config.KafkaConfiguration;
import com.techcrack.quizNotification.model.EmailNotificationModel;
import com.techcrack.quizNotification.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationConsumer {

    private final NotificationService<EmailNotificationModel> emailNotify;

    @KafkaListener(topics = "${app.kafka.notification-topic}", groupId = "notification-group")
    public void sendEmailNotify(EmailNotificationModel emailNotificationModel) {
        System.out.println("KAFKA DATA Consumed : " + emailNotificationModel);

        emailNotify.sendNotification(emailNotificationModel);
    }

}
