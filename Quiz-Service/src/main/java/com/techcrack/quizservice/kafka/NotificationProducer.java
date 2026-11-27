package com.techcrack.quizservice.kafka;

import com.techcrack.quizservice.model.emailNotification.EmailNotificationModel;
import com.techcrack.quizservice.notification.email.abstraction.EmailProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationProducer {
    private KafkaTemplate<String, EmailNotificationModel> kafkaTemplate;
    private KafkaConfiguration kafkaConfiguration;

    public boolean produceEmailNotification(EmailProducer data) {
        EmailNotificationModel emailNotificationModel = new EmailNotificationModel(
                data.to(),
                data.subject(),
                data.body()
        );

        kafkaTemplate.send(kafkaConfiguration.getNotificationTopic(), "email-notify", emailNotificationModel);

        return true;
    }
}
