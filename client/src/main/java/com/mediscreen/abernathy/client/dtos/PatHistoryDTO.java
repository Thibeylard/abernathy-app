package com.mediscreen.abernathy.client.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;


public class PatHistoryDTO {

    //Nullable
    private String id;
    @NotBlank
    private String patientId;
    @NotBlank
    private String content;

    private PatHistoryDTO() {
    }

    public PatHistoryDTO(String id, String patientId, @NotBlank String content) {
        this.id = id;
        this.patientId = patientId;
        this.content = content;
    }

    public PatHistoryDTO(String patientId, @NotBlank String content) {
        this.patientId = patientId;
        this.content = content;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // Made it Optional<String> in the first place but had to cancel due to Thymeleaf issues
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
