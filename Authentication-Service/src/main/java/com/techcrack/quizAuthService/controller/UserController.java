package com.techcrack.quizAuthService.controller;

import com.techcrack.quizAuthService.dto.AuthenticatedResponse;
import com.techcrack.quizAuthService.dto.AuthenticationRequest;
import com.techcrack.quizAuthService.dto.LoggedInUserDetail;
import com.techcrack.quizAuthService.dto.UserDto;
import com.techcrack.quizAuthService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/users/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
        AuthenticatedResponse authenticationResponse =  service.authenticateUser(
                request
        );

        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("/users/me")
    public ResponseEntity<LoggedInUserDetail> getCurrentUserId() {
        LoggedInUserDetail currentUser = service.getCurrentUser();

        return ResponseEntity.ok(currentUser);
    }
}
