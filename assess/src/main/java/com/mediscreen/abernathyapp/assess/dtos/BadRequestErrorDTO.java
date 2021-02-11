package com.mediscreen.abernathyapp.assess.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class BadRequestErrorDTO {

    private final HttpStatus status;
    private final Instant instant;
    private final String faultyParameter;
    private final String message;

    public BadRequestErrorDTO(HttpStatus status, Instant instant, String faultyParameter, String message) {
        this.status = status;
        this.instant = instant;
        this.faultyParameter = faultyParameter;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Instant getInstant() {
        return instant;
    }

    public String getFaultyParameter() {
        return faultyParameter;
    }

    public String getMessage() {
        return message;
    }
}
