package com.job_portal_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.job_portal_backend.dto.JobResponse;
import com.job_portal_backend.dto.JobSearchRequest;
import com.job_portal_backend.services.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping("/allJob")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{JobId}")
    public ResponseEntity<JobResponse> getJobByJobId(@PathVariable long jobId) {
        JobResponse job = jobService.getJobByJobId(jobId);
        return ResponseEntity.ok(job);
    }

    @PostMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(@RequestBody JobSearchRequest request) {
        return ResponseEntity.ok(jobService.searchJobs(request));
    }

}
