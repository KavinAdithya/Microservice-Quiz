package com.techcrack.quizservice.model.emailNotification;

import java.util.UUID;

public record EmailNotificationModel(UUID eventId, String to, String subject, String body) {
}
