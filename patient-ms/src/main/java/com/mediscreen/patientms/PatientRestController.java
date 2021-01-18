package com.mediscreen.patientms;

import com.mediscreen.patientms.models.Patient;
import com.mediscreen.patientms.repositories.PatientRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RepositoryRestController
public class PatientRestController {

    private final PatientRestRepository restRepository;

    @Autowired
    public PatientRestController(PatientRestRepository restRepository) {
        this.restRepository = restRepository;
    }

    @PostMapping(value = "patient/add")
    public ResponseEntity<?> addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) throws ParseException {
        Patient  newPatient = new Patient(
                family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);

        restRepository.save(newPatient);

        EntityModel<Patient> entityModel = new EntityModel<>(newPatient);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

}
