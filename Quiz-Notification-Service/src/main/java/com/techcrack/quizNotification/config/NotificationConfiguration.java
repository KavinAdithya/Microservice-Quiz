package com.techcrack.quizNotification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "notification.email-service")
@Component
@Getter
@Setter
public class NotificationConfiguration {
//    private String from;
}
