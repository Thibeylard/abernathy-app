package com.mediscreen.abernathyapp.patient.models;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(types = Patient.class, name = "healthInfos")
public interface PatientHealthInfos {

    public String getId();

    public String getFamily();

    public String getGiven();

    public LocalDate getDob();

    public String getSex();
}
