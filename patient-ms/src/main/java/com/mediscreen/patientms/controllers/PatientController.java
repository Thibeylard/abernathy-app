package com.mediscreen.patientms.controllers;

import com.mediscreen.patientms.models.Patient;
import com.mediscreen.patientms.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class PatientController {

    private final PatientRepository restRepository;
    private final EntityLinks entityLinks;

    @Autowired
    public PatientController(PatientRepository restRepository, EntityLinks entityLinks) {
        this.restRepository = restRepository;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/patient/add")
    public @ResponseBody ResponseEntity<?> addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {

        try {
            Patient newPatient = new Patient(
                    family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);

            newPatient = restRepository.save(newPatient);

            EntityModel<Patient> entityModel = new EntityModel<>(newPatient);
            entityModel.add(entityLinks.linkToItemResource(Patient.class,newPatient.getId().orElseThrow(InternalError::new)).withSelfRel());

            return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
        } catch (ParseException e) {
            // TODO Add error details
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InternalError e) {
            // TODO change InternalError for more precise Exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
