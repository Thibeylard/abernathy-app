package com.mediscreen.abernathyapp.patHistory.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.patHistory.dtos.ErrorDTO;
import com.mediscreen.abernathyapp.patHistory.services.PatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class PatHistoryController {

    private final PatHistoryService patHistoryService;
    private final ObjectMapper mapper;

    @Autowired
    public PatHistoryController(PatHistoryService patHistoryService, ObjectMapper mapper) {
        this.patHistoryService = patHistoryService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/patHistory/countTerms", produces = "application/json")
    public ResponseEntity<String> countTermsInPatHistory(
            @RequestParam("patientId") String patientId,
            @RequestParam("terminology") Set<String> terminology) throws JsonProcessingException {
        if (terminology.isEmpty()) {
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(new ErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    new ArrayList<>(List.of("Terminology cannot be empty."))
            )));
        }

        try {
            return ResponseEntity.ok(mapper.writeValueAsString(
                    patHistoryService.terminologySearch(patientId, terminology)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(new ErrorDTO(
                    HttpStatus.BAD_REQUEST,
                    Instant.now(),
                    new ArrayList<>(List.of(e.getMessage()))
            )));
        }


    }
}
