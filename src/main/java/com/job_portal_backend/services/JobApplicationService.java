package com.job_portal_backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.job_portal_backend.dto.JobApplicationRequest;
import com.job_portal_backend.dto.JobApplicationResponse;
import com.job_portal_backend.enums.ApplicationStatus;
import com.job_portal_backend.exception.JobNotAppliedException;
import com.job_portal_backend.exception.JobNotFoundException;
import com.job_portal_backend.exception.UnauthorizedJobAccessException;
import com.job_portal_backend.exception.UserNotFoundException;
import com.job_portal_backend.models.Job;
import com.job_portal_backend.models.JobApplication;
import com.job_portal_backend.models.User;
import com.job_portal_backend.repository.JobApplicationRepository;
import com.job_portal_backend.repository.JobRepository;
import com.job_portal_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JobRepository jobRepository;
    @Autowired
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplication applytoJob(JobApplicationRequest request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User applicant = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new JobNotFoundException("Job is not posted yet.."));

        if (jobApplicationRepository.existsByApplicantAndJob(applicant, job)) {
            throw new JobNotAppliedException("You have already applied to this job.");
        }

        JobApplication jobApplication = new JobApplication();

        jobApplication.setApplicant(applicant);
        jobApplication.setJob(job);
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setStatus(ApplicationStatus.UNDER_REVIEW);
        jobApplication.setResumeURL(request.getResumeURL());

        return jobApplicationRepository.save(jobApplication);

    }

    public List<JobApplicationResponse> getApplicationsByCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        List<JobApplication> jobApplications = jobApplicationRepository.findByApplicant(user);

        return jobApplications.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public List<JobApplicationResponse> getAllApplicant() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Recruiter not found"));

        List<Job> postedJob = jobRepository.findByRecruiter(recruiter);

        List<JobApplication> applications = postedJob.stream().flatMap(job -> jobApplicationRepository
                .findByJob(job).stream()).collect(Collectors.toList());
        return applications.stream().map(this::mapToResponseDTO).collect(Collectors.toList());

    }

    public void updateApplicationStatus(Long applicationId, String status) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Recruiter not found"));

        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new JobNotAppliedException("Application not found"));

        if (application.getJob().getRecruiter().getUserId() != recruiter.getUserId()) {
            throw new UnauthorizedJobAccessException("You are not authorized to update this application.");
        }

        try {
            ApplicationStatus statusEnum = ApplicationStatus.valueOf(status.toUpperCase());
            application.setStatus(statusEnum);
            jobApplicationRepository.save(application);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Status value. "+status);
        }

    }

    public void withdrawApplication(Long jobId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).
                orElseThrow(()-> new UserNotFoundException("User doesn't exist"));

        JobApplication application=jobApplicationRepository.findById(jobId)
                .orElseThrow(()-> new JobNotAppliedException("Application doesn't exist"));

        if(application.getApplicant().getUserId() != (user.getUserId())){
            throw new UnauthorizedJobAccessException("You are not allowd to delete this application");
        }    

        jobApplicationRepository.delete(application);    
    }

    private JobApplicationResponse mapToResponseDTO(JobApplication app) {
        return JobApplicationResponse.builder()
                .applicationId(app.getId())
                .resumeUrl(app.getResumeURL())
                .status(app.getStatus())
                .appliedDate(app.getAppliedDate())
                .jobId(app.getJob().getJobId())
                .jobTitle(app.getJob().getJobTitle())
                .applicantEmail(app.getApplicant().getEmail())
                .build();
    }

}
