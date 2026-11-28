package com.techcrack.quizNotification.consumer;

import com.techcrack.quizNotification.config.KafkaConfiguration;
import com.techcrack.quizNotification.model.EmailNotificationModel;
import com.techcrack.quizNotification.notification.NotificationService;
import com.techcrack.quizNotification.repository.NotificationEventRepository;
import com.techcrack.quizNotification.service.NotificationDuplicateDecorator;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationConsumer {

    private final NotificationService<EmailNotificationModel> emailNotify;
    private final NotificationEventRepository notificationEventRepository;

    @KafkaListener(topics = "${app.kafka.notification-topic}", groupId = "notification-group", concurrency = "3")
    public void sendEmailNotify(EmailNotificationModel emailNotificationModel) {
        System.out.println("KAFKA DATA Consumed : " + emailNotificationModel);

        new NotificationDuplicateDecorator<>(emailNotify, notificationEventRepository)
                .sendNotification(emailNotificationModel);
    }

}
