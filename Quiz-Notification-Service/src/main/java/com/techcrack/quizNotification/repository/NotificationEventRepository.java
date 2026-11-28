package com.techcrack.quizNotification.repository;

import com.techcrack.quizNotification.constant.Status;
import com.techcrack.quizNotification.entity.NotifiedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationEventRepository extends JpaRepository<NotifiedEvent, UUID> {

    @Modifying
    @Query(value = """
                INSERT INTO notification_events
                VALUES(event_id, processed_time, status)
                ON CONFLICT (event_id) DO NOTHING
            """, nativeQuery = true)
    int insertIfNoDuplicates(UUID eventId, LocalDateTime processedTime, Status status);
}
