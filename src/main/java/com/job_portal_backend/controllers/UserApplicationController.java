package com.job_portal_backend.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job_portal_backend.dto.JobApplicationRequest;
import com.job_portal_backend.dto.JobApplicationResponse;
import com.job_portal_backend.services.JobApplicationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/user/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@RequestBody JobApplicationRequest request) {
        jobApplicationService.applytoJob(request);
        return ResponseEntity.ok("Application submitted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getMyApplications() {
        List<JobApplicationResponse> applications = jobApplicationService.getApplicationsByCurrentUser();
        return ResponseEntity.ok(applications);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> withdrawApplication(@PathVariable Long jobId) {
        jobApplicationService.withdrawApplication(jobId);
        return ResponseEntity.ok("Application withdrawn successfully.");
    }
}

