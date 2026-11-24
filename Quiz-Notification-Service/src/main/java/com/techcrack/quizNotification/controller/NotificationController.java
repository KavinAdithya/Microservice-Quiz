package com.techcrack.quizNotification.controller;

import com.techcrack.quizNotification.notification.NotificationService;
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

    private NotificationService notificationService;

    @GetMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestParam("to") String to) {
        Map<String, String> data = new HashMap<>();

        data.put("to", to);
        data.put("subject", "Quiz Application Email Test");
        data.put("body", "Welcome Kavin \n \t Writing behalf of tech-crack this email is Testing purpose ignore it.");

        notificationService.sendNotification(data);

        return ResponseEntity.noContent()
                    .build();
    }
}
