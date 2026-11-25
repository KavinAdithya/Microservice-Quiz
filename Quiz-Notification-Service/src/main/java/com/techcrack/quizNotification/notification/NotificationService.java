package com.techcrack.quizNotification.notification;


public interface NotificationService<T> {
    void sendNotification(T data);
}
