package com.techcrack.quizNotification.controller;

import com.techcrack.quizNotification.model.EmailNotificationModel;
import com.techcrack.quizNotification.notification.NotificationService;
import com.techcrack.quizNotification.service.EmailNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class NotificationController {

    private NotificationService<EmailNotificationModel> notificationService;

    @GetMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestParam("to") String to) {
        String body = "Welcome Kavin \n \t Writing behalf of tech-crack this email is Testing purpose ignore it.";

        EmailNotificationModel emailNotificationModel = new EmailNotificationModel(to, "Quiz Application Email Test", body);

        notificationService.sendNotification(emailNotificationModel);

        return ResponseEntity.noContent()
                    .build();
    }
}
