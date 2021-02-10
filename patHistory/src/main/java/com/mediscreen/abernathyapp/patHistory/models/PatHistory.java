package com.mediscreen.abernathyapp.patHistory.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PatHistory {

    @Id
    private String id;
    private String patientId;
    @TextIndexed
    private String content;

    public PatHistory() {
    }

    public PatHistory(String patientId, String content) {
        this.patientId = patientId;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}