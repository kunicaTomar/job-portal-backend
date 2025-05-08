package com.job_portal_backend.services;

import org.springframework.stereotype.Service;

import com.job_portal_backend.dto.AdminDashboard;
import com.job_portal_backend.repository.JobApplicationRepository;
import com.job_portal_backend.repository.UserRepository;
import com.job_portal_backend.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public AdminDashboard getAdminDetails(){
        long userCount = userRepository.count();
        long jobCount = jobRepository.count();
        long jobApplicationCount = jobApplicationRepository.count();

        AdminDashboard dashboard = new AdminDashboard(userCount,jobCount,jobApplicationCount);

        return dashboard;
    }

}
