package com.mediscreen.abernathyapp.patient.services;

import com.mediscreen.abernathyapp.patient.models.Patient;
import com.mediscreen.abernathyapp.patient.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }

    @Override
    public Patient addPatient(String family, String given, Date dob, String sex, String address, String phone) throws IllegalArgumentException {
        Patient newPatient = new Patient(
                family, given, dob, sex, address, phone);

        return patientRepository.save(newPatient);
    }

    @Override
    public Patient updatePatient(String id, String family, String given, Date dob, String sex, String address, String phone) throws IllegalArgumentException {
        Patient newPatient = new Patient(
                family, given, dob, sex, address, phone);
        newPatient.setId(id);

        return patientRepository.save(newPatient);
    }
}
