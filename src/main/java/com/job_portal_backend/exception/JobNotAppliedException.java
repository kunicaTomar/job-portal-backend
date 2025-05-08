package com.job_portal_backend.exception;

public class JobNotAppliedException extends RuntimeException{
    public JobNotAppliedException(String message) {
        super(message);
    }
}
