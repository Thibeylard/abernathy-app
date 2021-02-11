package com.mediscreen.abernathyapp.patHistory.controllers;

import com.mediscreen.abernathyapp.patHistory.dtos.BadRequestErrorDTO;
import com.mediscreen.abernathyapp.patHistory.services.PatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Set;

@RepositoryRestController
public class PatHistoryController {

    private PatHistoryService patHistoryService;

    @Autowired
    public PatHistoryController(PatHistoryService patHistoryService) {
        this.patHistoryService = patHistoryService;
    }

    @GetMapping("/patHistory/assess")
    public ResponseEntity<?> assessOnPatHistoryCollection(
            @RequestParam("patientId") String patientId,
            @RequestParam("terminology") Set<String> terminology) {
        if (terminology.isEmpty()) {
            return ResponseEntity.badRequest().body(new BadRequestErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    "terminology",
                    "Terminology cannot be empty."
            ));
        }

        try {
            return ResponseEntity.ok(patHistoryService.terminologySearch(patientId, terminology));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new BadRequestErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    "patientId",
                    e.getMessage()
            ));
        }


    }
}
