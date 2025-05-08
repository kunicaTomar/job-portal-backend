package com.job_portal_backend.dto;

import java.time.LocalDate;

import com.job_portal_backend.enums.ApplicationStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobApplicationResponse {
    private Long applicationId;
    private String applicantEmail;
    private Long jobId;
    private String jobTitle;
    private String resumeUrl;
    private LocalDate appliedDate;
    private ApplicationStatus status;
}

