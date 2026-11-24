package com.techcrack.quizservice.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("app.kafka")
public class KafkaConfiguration {
    private String notificationTopic;
}
