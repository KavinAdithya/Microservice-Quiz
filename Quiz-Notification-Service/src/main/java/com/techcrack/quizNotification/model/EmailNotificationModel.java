package com.techcrack.quizNotification.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailNotificationModel extends AbstractNotificationModel {
    private String to;
    private String subject;
    private String body;

    public EmailNotificationModel(UUID eventId, String to, String subject, String body) {
        super(eventId);
    }
}
