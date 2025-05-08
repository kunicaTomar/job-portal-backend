package com.job_portal_backend.dto;

import java.util.Set;

import com.job_portal_backend.enums.Role;
import com.job_portal_backend.enums.UserType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private String contactNumber;
    private UserType type;
    private Set<Role> roles;
}
