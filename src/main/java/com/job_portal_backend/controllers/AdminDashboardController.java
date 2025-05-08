package com.job_portal_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job_portal_backend.dto.AdminDashboard;
import com.job_portal_backend.services.AdminDashboardService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {
    private AdminDashboardService adminDashboardService;

    @GetMapping
    public ResponseEntity<AdminDashboard> getAdminDashboard(){
        AdminDashboard adminDashboard=adminDashboardService.getAdminDetails();
        return ResponseEntity.ok(adminDashboard);
    }
    
}
