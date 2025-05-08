package com.job_portal_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboard {
    private long totalUsers;
    private long totalJobs;
    private long totalApplications;
}
