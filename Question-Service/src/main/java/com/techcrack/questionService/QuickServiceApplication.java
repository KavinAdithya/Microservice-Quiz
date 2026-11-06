package com.techcrack.questionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class QuickServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickServiceApplication.class);
    }
}
