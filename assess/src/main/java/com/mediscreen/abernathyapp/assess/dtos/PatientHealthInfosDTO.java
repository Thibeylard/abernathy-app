package com.mediscreen.abernathyapp.assess.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

public class PatientHealthInfosDTO {
    private String id;
    private String family;
    private String given;
    private LocalDate dob;
    private String sex;

    public PatientHealthInfosDTO() {
    }

    public PatientHealthInfosDTO(String id, String family, String given, LocalDate dob, String sex) {
        this.id = id;
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return this.id;
    }

    public String getFamily() {
        return family;
    }

    public String getGiven() {
        return given;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }


}
