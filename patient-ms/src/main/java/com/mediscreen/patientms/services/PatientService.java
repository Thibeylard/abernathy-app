package com.mediscreen.patientms.services;

import com.mediscreen.patientms.models.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

public interface PatientService {

    Patient addPatient(
            String family,
            String given,
            String dob,
            String sex,
            String address,
            String phone) throws ParseException;

    Patient updatePatient(
            String id,
            String family,
            String given,
            String dob,
            String sex,
            String address,
            String phone) throws ParseException;
}
