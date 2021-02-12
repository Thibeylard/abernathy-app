package com.mediscreen.abernathyapp.assess.controllers;

import com.mediscreen.abernathyapp.assess.dtos.BadRequestErrorDTO;
import com.mediscreen.abernathyapp.assess.services.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestController
public class AssessController {

    public AssessService assessService;

    @Autowired
    public AssessController(AssessService assessService) {
        this.assessService = assessService;
    }

    @GetMapping("/assess/id")
    public ResponseEntity<?> assessPatientById(@RequestParam("patId") String patId) {
        try {
            return ResponseEntity.ok(assessService.assessPatientDiabeteStatus(patId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new BadRequestErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    "patId",
                    "This patient ID does not refer to any existent patient."
            ));
        }
    }

    @GetMapping("/assess/familyGiven")
    public ResponseEntity<?> assessPatientByFamilyAndGiven(
            @RequestParam("familyName") String familyName,
            @RequestParam("givenName") String givenName) {
        try {
            return ResponseEntity.ok(assessService.assessPatientDiabeteStatus(familyName, givenName));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new BadRequestErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    "family, given",
                    "This family given couple does not refer to any existent patient."
            ));
        }
    }
}
