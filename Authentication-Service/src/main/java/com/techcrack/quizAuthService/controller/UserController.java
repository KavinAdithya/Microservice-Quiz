package com.techcrack.quizAuthService.controller;

import com.techcrack.quizAuthService.dto.UserDto;
import com.techcrack.quizAuthService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        service.createUser(user);
        return ResponseEntity.noContent()
                .build();
    }
}
