package com.mediscreen.abernathy.client.patient.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mediscreen.abernathy.client.patient.annotations.ValidDobFormat;
import com.mediscreen.abernathy.client.patient.annotations.ValidPhoneFormat;
import com.mediscreen.abernathy.client.patient.annotations.ValidSexValue;

import javax.validation.constraints.NotBlank;


public class PatientDTO {

    //Nullable
    private String id;

    @NotBlank
    private String family;

    @NotBlank
    private String given;

    @ValidDobFormat
    private String dob;

    @ValidSexValue
    private String sex;

    @NotBlank
    private String address;

    @ValidPhoneFormat
    private String phone;

    private PatientDTO() {
    }

    public PatientDTO(String id, String family, String given, String dob, String sex, String address, String phone) {
        this.id = id;
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public PatientDTO(String family, String given, String dob, String sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // Made it Optional<String> in the first place but had to cancel due to Thymeleaf issues
    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
