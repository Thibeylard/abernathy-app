package com.mediscreen.abernathyapp.patHistory.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;

public class ErrorDTO {

    private HttpStatus status;
    private Instant instant;
    private ArrayList<String> messages;

    public ErrorDTO() {

    }
    
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

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
