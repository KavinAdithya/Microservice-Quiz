package com.techcrack.quizservice.kafka;

import com.techcrack.quizservice.model.EmailNotificationModel;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationPublish {
    private KafkaTemplate<String, EmailNotificationModel> kafkaTemplate;
    private KafkaConfiguration kafkaConfiguration;

    public boolean publishNotify(EmailNotificationModel data) {
        kafkaTemplate.send(kafkaConfiguration.getNotificationTopic(), data);

        return true;
    }
}
