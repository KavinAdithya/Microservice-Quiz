package com.techcrack.quizNotification.entity;

import com.techcrack.quizNotification.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifiedEvent {

    @Id
    private UUID eventId;
    private LocalDateTime processedTime;

    @Enumerated(value = EnumType.STRING)
    private Status status;

}
