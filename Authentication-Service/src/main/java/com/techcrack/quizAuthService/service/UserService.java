package com.techcrack.quizAuthService.service;

import com.techcrack.quizAuthService.dto.UserDto;
import com.techcrack.quizAuthService.entity.User;
import com.techcrack.quizAuthService.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setUsername(userDto.username());
        user.setPassword(encoder.encode(userDto.password()));
        repository.save(user);
    }
}
