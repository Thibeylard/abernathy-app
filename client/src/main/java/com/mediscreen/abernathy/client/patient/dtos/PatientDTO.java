package com.mediscreen.abernathy.client.patient.dtos;

import com.mediscreen.abernathy.client.patient.annotations.ValidDobFormat;
import com.mediscreen.abernathy.client.patient.annotations.ValidPhoneFormat;
import com.mediscreen.abernathy.client.patient.annotations.ValidSexValue;

import javax.validation.constraints.NotBlank;

public class PatientDTO {

    @NotBlank
    private String family;

    @NotBlank
    private String given;

    @NotBlank
    @ValidDobFormat
    private String dob;

    @NotBlank
    @ValidSexValue
    private String sex;

    @NotBlank
    private String address;

    @NotBlank
    @ValidPhoneFormat
    private String phone;

    private PatientDTO() {
    }

    public PatientDTO(String family, String given, String dob, String sex, String address, String phone) {
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
