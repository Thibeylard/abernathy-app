package com.mediscreen.abernathyapp.patHistory.controllers;

import com.mediscreen.abernathyapp.patHistory.services.PatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static org.springframework.http.ResponseEntity.status;

@RepositoryRestController
public class PatHistoryController {

    private PatHistoryService patHistoryService;

    @Autowired
    public PatHistoryController(PatHistoryService patHistoryService) {
        this.patHistoryService = patHistoryService;
    }

    @GetMapping("/patHistory/assess")
    public ResponseEntity<Integer> assessOnPatHistoryCollection(
            @RequestParam("patientId") String patientId,
            @RequestParam("terminology") Set<String> terminology) {
        return ResponseEntity.ok(0);
    }
}
