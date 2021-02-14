package com.mediscreen.abernathyapp.patHistory.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;

public class ErrorDTO {

    private final HttpStatus status;
    private final Instant instant;
    private final ArrayList<String> messages;

    public ErrorDTO(HttpStatus status, Instant instant, ArrayList<String> messages) {
        this.status = status;
        this.instant = instant;
        this.messages = messages;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Instant getInstant() {
        return instant;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
