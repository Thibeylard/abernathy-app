package com.mediscreen.abernathyapp.patient.services;

import com.mediscreen.abernathyapp.patient.models.Patient;

import java.util.Date;

public interface PatientService {

    Patient addPatient(
            String family,
            String given,
            Date dob,
            String sex,
            String address,
            String phone);

    Patient updatePatient(
            String id,
            String family,
            String given,
            Date dob,
            String sex,
            String address,
            String phone);
}
