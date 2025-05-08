package com.job_portal_backend.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job_portal_backend.dto.UserRequest;
import com.job_portal_backend.dto.UserResponse;
import com.job_portal_backend.services.UserService;

import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    //admin can get all the user details\
    // user can update/edit and delete its profile 
    private final UserService userService;

    // @GetMapping("/allUser")
    // @PreAuthorize("hasAnyRole('ADMIN','RECRIUTER')")
    // public ResponseEntity<List<UserResponse>> getAllUsers(){
    //     return ResponseEntity.ok(userService.getAllUsers());
    // }

    @GetMapping("/allUser")
@PreAuthorize("hasAnyRole('ADMIN','RECRIUTER')")
public ResponseEntity<Page<UserResponse>> getAllUsers(
        @PageableDefault(size = 10, sort = "userId") Pageable pageable) {
    return ResponseEntity.ok(userService.getAllUsers(pageable));
}


    @GetMapping("/myProfile")
    @PreAuthorize("hasAnyRole('USER','ADMIN','RECRIUTER')")
    public ResponseEntity<UserResponse> getMyProfile(){
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PutMapping("/myProfile")
    @PreAuthorize("hasAnyRole('USER','ADMIN','RECRIUTER')")
    public ResponseEntity<UserResponse> updateMyProfile(@RequestBody UserRequest request){
        System.out.println("Controller check:"+request);
        return ResponseEntity.ok(userService.updateMyProfile(request));
    }

    @DeleteMapping("/myProfile")
    @PreAuthorize("hasAnyRole('USER','ADMIN','RECRIUTER')")
    public ResponseEntity<String> deleteMyProfile(){
        userService.deleteCurrentProfile();
        return ResponseEntity.ok("Your Profile successfully Deleted!!");
    }

    // @GetMapping("/search")
    // @PreAuthorize("hasRole('ADMIN','RECRUITER')")
    // public ResponseEntity<List<UserResponse>> searchUser(@RequestParam("q") String keywords){
    //     return ResponseEntity.ok(userService.searchUsers(keywords));
    // }
}
