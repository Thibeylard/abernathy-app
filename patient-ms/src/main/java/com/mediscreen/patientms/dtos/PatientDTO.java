package com.mediscreen.patientms.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mediscreen.patientms.annotations.ValidDob;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientDTO {
    @NotBlank
    private String family;
    @NotBlank
    private String given;
    @ValidDob
    private String dob;
    @NotBlank
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

    public PatientDTO(String family, String given, String dob, String sex, String address, String phone) throws ParseException {
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

    public String getDob() {
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