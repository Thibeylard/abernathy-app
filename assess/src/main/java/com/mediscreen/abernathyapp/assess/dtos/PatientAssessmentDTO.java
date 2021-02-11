package com.mediscreen.abernathyapp.assess.dtos;

public class PatientAssessmentDTO {

    private String family;
    private String given;
    private int age;

    public PatientAssessmentDTO() {
    }

    public PatientAssessmentDTO(String family, String given, int age) {
        this.family = family;
        this.given = given;
        this.age = age;
    }


    public String getFamily() {
        return family;
    }

    public String getGiven() {
        return given;
    }

    public int getAge() {
        return age;
    }
}
