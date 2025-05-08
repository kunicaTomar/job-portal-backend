package com.job_portal_backend.exception;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message){
        super(message);
    }
}
