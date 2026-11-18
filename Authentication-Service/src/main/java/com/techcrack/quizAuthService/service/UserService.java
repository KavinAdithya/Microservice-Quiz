package com.techcrack.quizAuthService.service;

import com.techcrack.quizAuthService.dto.AuthenticatedResponse;
import com.techcrack.quizAuthService.dto.AuthenticationRequest;
import com.techcrack.quizAuthService.dto.UserCredentialModel;
import com.techcrack.quizAuthService.dto.UserDto;
import com.techcrack.quizAuthService.entity.CustomUserDetail;
import com.techcrack.quizAuthService.entity.User;
import com.techcrack.quizAuthService.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository repository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setUsername(userDto.username());
        user.setPassword(encoder.encode(userDto.password()));
        repository.save(user);
    }

    public Optional<User> getAuthenticationDetails(String username) {
        return repository.findByUsername(username);
    }

    public AuthenticatedResponse authenticateUser(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.username(), List.of("USER"));

        return new AuthenticatedResponse(authentication.getName(), token);
    }

    public long getCurrentUserId() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert userDetails != null;

        return userDetails.getUser().getId();
    }
}
