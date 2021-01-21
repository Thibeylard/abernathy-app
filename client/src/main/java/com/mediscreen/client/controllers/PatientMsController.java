package com.mediscreen.client.controllers;

import com.mediscreen.client.proxies.PatientMsProxy;
import com.mediscreen.patientms.dtos.PatientDTO;
import com.mediscreen.patientms.models.Patient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientMsController {


    private final Logger logger;
    private final PatientMsProxy patientMsProxy;

    @Autowired
    public PatientMsController(@Qualifier("getClientPatientLogger") Logger logger, PatientMsProxy patientMsProxy) {
        this.logger = logger;
        this.patientMsProxy = patientMsProxy;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/getPatient/{id}")
    public String getPatient(@PathVariable("id") int id, Model model) {
        return "patientForm";
    }

    @PostMapping(value = "/patientForm")
    public String addPatient(@RequestBody PatientDTO patientDTO) {
        return "patientForm";
    }

   /* @PutMapping(value = "/patientForm")
    public String updatePatient(@RequestBody PatientDTO patientDTO) {
        return "patientForm";
    }*/
    
    @ModelAttribute("allPatients")
    private List<Patient> getAllPatients() {
        return patientMsProxy.getAllPatients();
    }

    @ModelAttribute("patient")
    private List<Patient> getSpecificPatient(String id) {
        return patientMsProxy.getPatient(id);
    }
}
