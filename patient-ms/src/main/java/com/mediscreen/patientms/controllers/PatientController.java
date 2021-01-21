package com.mediscreen.patientms.controllers;

import com.mediscreen.patientms.annotations.ValidDob;
import com.mediscreen.patientms.models.Patient;
import com.mediscreen.patientms.repositories.PatientRepository;
import com.mediscreen.patientms.services.PatientService;
import org.slf4j.Logger;
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

    private final Logger logger;
    private final PatientService patientService;
    private final EntityLinks entityLinks;

    @Autowired
    public PatientController(Logger logger, PatientService patientService, EntityLinks entityLinks) {
        this.patientService = patientService;
        this.logger = logger;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/patient/add")
    public @ResponseBody ResponseEntity<?> addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") @ValidDob String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {

        try {
            Patient newPatient = patientService.addPatient(
                    family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);

            EntityModel<Patient> entityModel = new EntityModel<>(newPatient);
            entityModel.add(entityLinks.linkToItemResource(Patient.class,newPatient.getId().orElseThrow(InternalError::new)).withSelfRel());

            return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
        } catch (ParseException e) {
            logger.debug("Date of birth validation did not work although date format is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InternalError e) {
            // TODO change InternalError for more precise Exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/patient/update")
    public @ResponseBody ResponseEntity<?> updatePatient(
            @RequestParam("id") String id,
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") @ValidDob String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {

        try {
            Patient newPatient = patientService.updatePatient(
                    id, family, given, new SimpleDateFormat("yyyy-MM-dd").parse(dob), sex, address, phone);

            EntityModel<Patient> entityModel = new EntityModel<>(newPatient);
            entityModel.add(entityLinks.linkToItemResource(Patient.class, id).withSelfRel());

            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } catch (ParseException e) {
            logger.debug("Date of birth validation did not work although date format is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InternalError e) {
            // TODO change InternalError for more precise Exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
