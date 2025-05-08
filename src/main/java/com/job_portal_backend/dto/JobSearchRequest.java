package com.job_portal_backend.dto;

import java.time.LocalDate;
import java.util.Set;

import com.job_portal_backend.enums.JobType;
import com.job_portal_backend.enums.WorkModel;

import lombok.Data;

@Data
public class JobSearchRequest {
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String location;
    private JobType type;
    private WorkModel workModel;
    private Set<String> skills;
    private String qualification;
    private LocalDate jobpostedOn;
    private LocalDate applicattionLastDate;

    private String sortBy;
    private String sortDirection;
}
