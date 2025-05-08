// service/AuthService.java
package com.job_portal_backend.services;

import com.job_portal_backend.dto.AuthRequest;
import com.job_portal_backend.dto.RegisterRequest;

public interface AuthService {
    String login(AuthRequest loginDto);
    String register(RegisterRequest registerDto);

}
