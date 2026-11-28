package com.techcrack.quizNotification.service;

import com.techcrack.quizNotification.constant.Status;
import com.techcrack.quizNotification.entity.NotifiedEvent;
import com.techcrack.quizNotification.model.AbstractNotificationModel;
import com.techcrack.quizNotification.notification.NotificationService;
import com.techcrack.quizNotification.repository.NotificationEventRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class NotificationDuplicateDecorator<T extends AbstractNotificationModel> implements NotificationService<T>  {
    private final NotificationService<T> notify;
    private final NotificationEventRepository notificationEventRepository;

    @Override
    public void sendNotification(T data) {
        int duplicates = notificationEventRepository.insertIfNoDuplicates(data.getEventId(), LocalDateTime.now(), Status.PROCESSING);

        if (duplicates == 0) {
            System.out.println("Email Already processed with id " + data.getEventId());
            return;
        }

        notify.sendNotification(data);

        NotifiedEvent event = notificationEventRepository.findById(data.getEventId())
                                                        .orElseThrow();
        event.setStatus(Status.SENT);

        notificationEventRepository.save(event);
    }
}
