package com.job_portal_backend.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {
    @NotNull(message = "Job ID cannot be null")
    private Long jobId;

    @NotNull(message = "Resume URL cannot be null")
    private String resumeURL;

}
