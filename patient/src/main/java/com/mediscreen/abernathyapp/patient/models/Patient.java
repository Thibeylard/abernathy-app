package com.mediscreen.abernathyapp.patient.models;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Optional;

public class Patient {
    @Id
    private String id;
    private String family;
    private String given;
    private Instant dob;
    private String sex;
    private String address;
    private String phone;

    private Patient() {
    }

    public Patient(String family, String given, Instant dob, String sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Optional<String> getId() {
        return Optional.of(this.id);
    }

    public void setId(String id) {
        this.id = id;
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

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
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