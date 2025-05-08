package com.job_portal_backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.job_portal_backend.dto.UserRequest;
import com.job_portal_backend.dto.UserResponse;
import com.job_portal_backend.exception.UserNotFoundException;
import com.job_portal_backend.models.User;
import com.job_portal_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // public List<UserResponse> getAllUsers(){
    //     return userRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    // }
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                             .map(this::mapToResponseDTO);
    }
    

    public UserResponse getCurrentUserProfile(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        return mapToResponseDTO(user);
    }

    public UserResponse updateMyProfile( UserRequest request){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        System.out.println("My Request: "+request);
        user.setUsername(request.getUsername());
        
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());
        user.setType(request.getType());

        userRepository.save(user);

        return mapToResponseDTO(user);
    }

    public void deleteCurrentProfile(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        
        userRepository.delete(user);
    }

    // public List<UserResponse> searchUsers(String keywords){
    //     List<User> users = userRepository.searchUsers(keywords);
    //     return users.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    // }

    private UserResponse mapToResponseDTO(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .type(user.getType())
                .roles(user.getRoles())
                .build();
    }

    
    
}
