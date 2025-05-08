package com.job_portal_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job_portal_backend.models.Job;
import com.job_portal_backend.models.JobApplication;
import com.job_portal_backend.models.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByApplicantAndJob(User applicant, Job job);

    List<JobApplication> findByApplicant(User user);

    List<JobApplication> findByJob(Job job);

}
