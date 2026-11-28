package com.techcrack.quizNotification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractNotificationModel {
    private UUID eventId;
}
