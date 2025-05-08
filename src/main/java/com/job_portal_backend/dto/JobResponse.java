package com.job_portal_backend.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.job_portal_backend.enums.JobType;
import com.job_portal_backend.enums.WorkModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse{
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String location;
    private JobType type;
    private WorkModel workModel;
    private Set<String> skills;
    private String qualification;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate jobpostedOn;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate applicattionLastDate;
    private String postedBy;
    private int vacancy;

    
}
