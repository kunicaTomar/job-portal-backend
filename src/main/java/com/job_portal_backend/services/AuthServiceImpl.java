package com.job_portal_backend.services;

import com.job_portal_backend.dto.AuthRequest;
import com.job_portal_backend.dto.RegisterRequest;
import com.job_portal_backend.enums.Role;
import com.job_portal_backend.exception.EmailAlreadyExistsException;
import com.job_portal_backend.exception.InvalidCredentialsException;
import com.job_portal_backend.models.User;
import com.job_portal_backend.repository.UserRepository;
import com.job_portal_backend.security.JwtTokenProvider;
import lombok.AllArgsConstructor;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already in use");
        }

        User user = new User();
        user.setUsername(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setContactNumber(registerDto.getContactNumber());
        user.setType(registerDto.getType());

        if (registerDto.getRoles() != null && !registerDto.getRoles().isEmpty()) {
            user.setRoles(registerDto.getRoles());
        } else {
            user.setRoles(Set.of(Role.USER));
        }

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String login(AuthRequest loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsernameOrEmail(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.generateToken(authentication);
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }
    }
}
