package com.mediscreen.client.unit.controllers;

import com.mediscreen.client.proxies.PatientMsProxy;
import com.mediscreen.patientms.dtos.PatientDTO;
import com.mediscreen.patientms.models.Patient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        model.addAttribute(getAllPatients());
        return "home";
    }

    @GetMapping("/patient/{id}")
    public String getPatient(@PathVariable("id") String id, Model model) {
        Patient patient = getPatient(id);
        if(patient == null) {
            model.addAttribute("patientNotFound", true);
            return "home";
        }
        model.addAttribute(getPatient(id));
        return "patientForm";
    }

    @PostMapping(value = "/patient")
    public String addPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("patient", patientDTO);
        } else {
            patientMsProxy.addPatient(patientDTO);
            model.addAttribute("patientAdded", true);
            return "home";
        }
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
    private Patient getPatient(String id) {
        return patientMsProxy.getPatient(id);
    }
}
