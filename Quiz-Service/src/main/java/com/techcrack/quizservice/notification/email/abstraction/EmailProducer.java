package com.techcrack.quizservice.notification.email.abstraction;

public interface EmailProducer {
    String to();
    String subject();
    String body();
}
