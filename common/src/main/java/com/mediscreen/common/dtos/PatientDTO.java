package com.mediscreen.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class PatientDTO {
    @NotBlank
    private String family;
    @NotBlank
    private String given;
    @NotBlank
    private Date dob;
    @NotBlank()
    @Size(min = 1, max = 1)
    private String sex;
    @NotBlank
    //TODO Add custom address validator
    private String address;
    @NotBlank
    //TODO Add custom phone validator
    private String phone;

    @JsonCreator
    private PatientDTO() {
    }

    public PatientDTO(String family, String given, Date dob, String sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public String getFamily() {
        return family;
    }

    public String getGiven() {
        return given;
    }

    public Date getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}