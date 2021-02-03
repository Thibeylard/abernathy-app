package com.mediscreen.abernathy.client.patient.controllers;

import com.mediscreen.abernathy.client.patient.dtos.PatientCollectionResourceDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientItemResourceDTO;
import com.mediscreen.abernathy.client.patient.proxies.AppPatientProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

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
    public String getPatientList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model model) {
        PatientCollectionResourceDTO collectionResource = getPatientCollectionResource(page, size);
        model.addAttribute("allPatients", collectionResource.getPatientItems());
        return "/patient/list";
    }

    @GetMapping("/patient/get")
    public String getPatient(@RequestParam("id") String id, Model model) {
        PatientItemResourceDTO patient = getPatientItemResource(id);
        if (patient == null) {
            model.addAttribute("patientNotFound", true);
            return "/patient/list";
        }
        model.addAttribute("item", patient.getItem());
        model.addAttribute("links", patient.getLinks());
        return "/patient/form";
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
            return "/patient/list";
        }
        return "/patient/form";
    }

    @PutMapping(value = "/patient/update")
    public String updatePatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patientDTO);
        } else {
            try {
                appPatientProxy.updatePatient(
                        patientDTO.getId().orElseThrow(ValidationException::new),
                        patientDTO.getFamily(),
                        patientDTO.getGiven(),
                        patientDTO.getDob(),
                        patientDTO.getSex(),
                        patientDTO.getAddress(),
                        patientDTO.getPhone()
                );
                model.addAttribute("patientUpdated", true);
                return "/patient/list";
            } catch (ValidationException e) {
                result.addError(new FieldError("patient", "id", "ID is mandatory."));
                model.addAttribute("patient", patientDTO);
            }
        }
        return "/patient/form";
    }


    private PatientCollectionResourceDTO getPatientCollectionResource(@Nullable Integer page,
                                                                      @Nullable Integer size) {
        return appPatientProxy.getAllPatients(page, size);
    }

    private PatientItemResourceDTO getPatientItemResource(String id) {
        return appPatientProxy.getPatient(id);
    }

}
