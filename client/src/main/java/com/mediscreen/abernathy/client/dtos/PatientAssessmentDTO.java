package com.mediscreen.abernathy.client.dtos;

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

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
