package com.techcrack.quizNotification.notification;

import java.util.Map;

public interface NotificationService {
    void sendNotification(Map<String, String> data);
}
