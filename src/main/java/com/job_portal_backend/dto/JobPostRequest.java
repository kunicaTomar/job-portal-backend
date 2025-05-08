package com.job_portal_backend.dto;

import java.util.Set;

import com.job_portal_backend.enums.JobType;
import com.job_portal_backend.enums.WorkModel;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JobPostRequest {
    private String jobTitle;
    private String jobDescription;
    private String location;
    private JobType type;
    private WorkModel workModel;
    private Set<String> skills;
    private String qualification;
    private String postedBy;
    private int vacancy;
}
