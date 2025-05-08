package com.job_portal_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job_portal_backend.dto.JobPostRequest;
import com.job_portal_backend.dto.JobResponse;
import com.job_portal_backend.services.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recruiter/job")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RECRIUTER')")
public class RecruiterJobController {
    private final JobService jobService;

    @PostMapping("/postJob")
    public ResponseEntity<JobResponse> postJob(@RequestBody JobPostRequest request) {
        JobResponse jobResponse = jobService.postJob(request);
        return ResponseEntity.ok(jobResponse); // returns JobResponseDTO as response
    }

    @GetMapping("/jobPostedByMe")
    public ResponseEntity<List<JobResponse>> getJobPostedByCurrentRecruiter() {
        List<JobResponse> job = jobService.getJobPostedByCurrentRecruiter();
        return ResponseEntity.ok(job);
    }
    
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> updateJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.ok("Delete Successfully.");
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long jobId, @RequestBody JobPostRequest request) {
        JobResponse job = jobService.updateJob(jobId, request);
        return ResponseEntity.ok(job);
    }

    

    
}
