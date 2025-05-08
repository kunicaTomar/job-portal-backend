package com.job_portal_backend.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.job_portal_backend.dto.JobApplicationResponse;
import com.job_portal_backend.services.JobApplicationService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/recruiter/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterApplicationController {

    private final JobApplicationService jobApplicationService;

    @GetMapping("/job-applicants")
    public ResponseEntity<List<JobApplicationResponse>> getAllApplicants() {
        List<JobApplicationResponse> applications = jobApplicationService.getAllApplicant();
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(@PathVariable Long applicationId,
                                                          @RequestParam("status") String status) {
        jobApplicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok("Status updated successfully.");
    }
}

