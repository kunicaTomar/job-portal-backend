package com.job_portal_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.job_portal_backend.models.Job;
import com.job_portal_backend.models.User;

public interface JobRepository extends JpaRepository<Job, Long>,JpaSpecificationExecutor<Job> {
    List<Job> findByRecruiter(User recruiter);

}
