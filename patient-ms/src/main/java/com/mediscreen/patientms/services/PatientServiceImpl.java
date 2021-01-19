package com.mediscreen.patientms.services;

import com.mediscreen.patientms.models.Patient;
import com.mediscreen.patientms.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }
    @Override
    public Patient addPatient(String family, String given, String dob, String sex, String address, String phone) throws ParseException {
            Patient newPatient = new Patient(
                    family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);

            return patientRepository.save(newPatient);
    }

    @Override
    public Patient updatePatient(String id, String family, String given, String dob, String sex, String address, String phone) throws ParseException {
        Patient newPatient = new Patient(
                family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);
        newPatient.setId(id);

        return patientRepository.save(newPatient);
    }
}
