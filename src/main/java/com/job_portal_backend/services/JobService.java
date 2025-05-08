package com.job_portal_backend.services;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.job_portal_backend.dto.JobPostRequest;
import com.job_portal_backend.dto.JobResponse;
import com.job_portal_backend.dto.JobSearchRequest;
import com.job_portal_backend.exception.JobNotFoundException;
import com.job_portal_backend.exception.UnauthorizedJobAccessException;
import com.job_portal_backend.exception.UserNotFoundException;
import com.job_portal_backend.models.Job;
import com.job_portal_backend.models.User;
import com.job_portal_backend.repository.JobRepository;
import com.job_portal_backend.repository.UserRepository;
import com.job_portal_backend.specification.JobSpecification;

@Service
@RequiredArgsConstructor
public class JobService {

    @Autowired
    private final JobRepository jobRepository;
    @Autowired
    private final UserRepository userRepository;

    public JobResponse postJob(JobPostRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Recruiter not found"));

        Job job = new Job();
        job.setJobTitle(request.getJobTitle());
        job.setJobDescription(request.getJobDescription());
        job.setLocation(request.getLocation());
        job.setType(request.getType());
        job.setWorkModel(request.getWorkModel());
        job.setSkills(request.getSkills());
        job.setQualification(request.getQualification());
        job.setVacancy(request.getVacancy());
        job.setJobpostedOn(LocalDate.now());
        job.setRecruiter(recruiter);

        Job saved = jobRepository.save(job);

        return mapToDTO(saved);
    }

    public List<JobResponse> searchJobs(JobSearchRequest request){
        List<Job> jobs = jobRepository.findAll(JobSpecification.getJobBySearch(request));
        return jobs.stream().map(this::mapToDTO).collect(Collectors.toList());
    }    

    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public JobResponse getJobByJobId(Long JobId) {
        Job job = jobRepository.findById(JobId).orElseThrow(() -> new JobNotFoundException("Job not found"));
        return mapToDTO(job);
    }

    public List<JobResponse> getJobPostedByCurrentRecruiter(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("Recruiter doesn't exist."));

        List<Job> job = jobRepository.findByRecruiter(recruiter);

        return job.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public JobResponse updateJob(Long jobId, JobPostRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Recruiter not found"));

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Id not found"));

        if (!job.getRecruiter().getEmail().equals(recruiter.getEmail())) {
            throw new UnauthorizedJobAccessException("you are not authorised to update this job");
        }

        job.setJobTitle(request.getJobTitle());
        job.setJobDescription(request.getJobDescription());
        job.setLocation(request.getLocation());
        job.setType(request.getType());
        job.setWorkModel(request.getWorkModel());
        job.setSkills(request.getSkills());
        job.setQualification(request.getQualification());
        job.setVacancy(request.getVacancy());

        return mapToDTO(jobRepository.save(job));
    }

    public void deleteJob(Long jobId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Recruiter not found"));

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Id not found"));

        if (!job.getRecruiter().getEmail().equals(recruiter.getEmail())) {
            throw new UnauthorizedJobAccessException("you are not authorised to update this job");
        }

        jobRepository.delete(job);
    }

    private JobResponse mapToDTO(Job job) {
        JobResponse dto = new JobResponse();
        dto.setJobId(job.getJobId());
        dto.setJobTitle(job.getJobTitle());
        dto.setJobDescription(job.getJobDescription());
        dto.setLocation(job.getLocation());
        dto.setType(job.getType());
        dto.setWorkModel(job.getWorkModel());
        dto.setSkills(job.getSkills());
        dto.setQualification(job.getQualification());
        dto.setJobpostedOn(job.getJobpostedOn());
        dto.setVacancy(job.getVacancy());
        return dto;
    }
}
