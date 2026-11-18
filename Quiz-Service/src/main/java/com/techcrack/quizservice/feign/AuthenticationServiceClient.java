package com.techcrack.quizservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("AUTHENTICATION-SERVICE")
public interface AuthenticationServiceClient {
    @GetMapping("/api/users/me/id")
    ResponseEntity<Long> getCurrentUserId();
}
