package com.mediscreen.abernathy.client.patient.dtos;

public class PatientDTO {

    private String family;
    private String given;
    private String dob;
    private String sex;
    private String address;
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
