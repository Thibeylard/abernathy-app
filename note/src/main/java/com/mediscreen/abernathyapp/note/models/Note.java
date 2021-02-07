package com.mediscreen.abernathyapp.note.models;

import org.springframework.data.annotation.Id;

//@JsonDeserialize(using = PatientDeserializer.class)
public class Note {
    @Id
    private String id;
    private String patientId;
    private String content;

    private Note() {
    }

    public Note(String patientId, String content) {
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