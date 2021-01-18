package com.mediscreen.patientms.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mediscreen.common.dtos.PatientDTO;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class Patient {
    @Id
    private UUID uuid;
    private String family;
    private String given;
    private Date dob;
    private String sex;
    private String address;
    private String phone;

    public Patient(PatientDTO dto) {
        this.family = dto.getFamily();
        this.given = dto.getGiven();
        this.dob = dto.getDob();
        this.sex = dto.getSex();
        this.address = dto.getAddress();
        this.phone = dto.getPhone();
    }

    public Patient(String family, String given, Date dob, String sex, String address, String phone) {
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

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}