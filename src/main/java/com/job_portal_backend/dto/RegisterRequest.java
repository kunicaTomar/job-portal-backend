package com.job_portal_backend.dto;

import com.job_portal_backend.enums.UserType;
import com.job_portal_backend.enums.Role;
import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "User Name is mandatory")
    private String userName;


    @NotBlank(message = "email is mandatory")
    @Email(message = "enter valid email")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @Positive
    private String contactNumber;
    private UserType type;

    private Set<Role> roles;

}
