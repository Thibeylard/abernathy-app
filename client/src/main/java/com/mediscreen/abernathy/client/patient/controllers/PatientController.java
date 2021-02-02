package com.mediscreen.abernathy.client.patient.controllers;

import com.mediscreen.abernathy.client.patient.dtos.PatientCollectionDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientItemDTO;
import com.mediscreen.abernathy.client.patient.proxies.AppPatientProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PatientController {


    private final Logger logger;
    private final AppPatientProxy appPatientProxy;

    @Autowired
    public PatientController(@Qualifier("getClientPatientLogger") Logger logger, AppPatientProxy appPatientProxy) {
        this.logger = logger;
        this.appPatientProxy = appPatientProxy;
    }

    @GetMapping("/patient/list")
    public String home(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model model) {
        model.addAttribute(getAllPatients(page, size));
        return "home";
    }

    @GetMapping("/patient/get")
    public String getPatient(@PathVariable("id") String id, Model model) {
        PatientItemDTO patient = getPatient(id);
        if (patient == null) {
            model.addAttribute("patientNotFound", true);
            return "home";
        }
        model.addAttribute(getPatient(id));
        return "patientForm";
    }

    @PostMapping(value = "/patient/add")
    public String addPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patientDTO);
        } else {
            appPatientProxy.addPatient(
                    patientDTO.getFamily(),
                    patientDTO.getGiven(),
                    patientDTO.getDob(),
                    patientDTO.getSex(),
                    patientDTO.getAddress(),
                    patientDTO.getPhone()
            );
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
    private PatientCollectionDTO getAllPatients(@Nullable Integer page,
                                                @Nullable Integer size) {
        return appPatientProxy.getAllPatients(page, size);
    }

    @ModelAttribute("patient")
    private PatientItemDTO getPatient(String id) {
        return appPatientProxy.getPatient(id);
    }
}
