package com.techcrack.quizApplicationRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class QuizApplicationRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizApplicationRegistryApplication.class, args);
	}
}
