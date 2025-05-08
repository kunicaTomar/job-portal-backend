package com.job_portal_backend.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.job_portal_backend.enums.JobType;
import com.job_portal_backend.enums.WorkModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Job")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long jobId;

    private String jobTitle;
    private String jobDescription;
    private String location;

    @Enumerated(EnumType.STRING)
    private JobType type;

    @Enumerated(EnumType.STRING)
    private WorkModel workModel;

    private Set<String> skills;
    private String qualification;

    private LocalDate jobpostedOn;
    private LocalDate applicattionLastDate;

    
    private int vacancy;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications;
    

    
}
