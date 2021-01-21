package com.mediscreen.patientms.services;

import com.mediscreen.patientms.models.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
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
