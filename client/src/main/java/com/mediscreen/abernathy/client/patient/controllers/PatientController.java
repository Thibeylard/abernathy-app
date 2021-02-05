package com.mediscreen.abernathy.client.patient.controllers;

import com.mediscreen.abernathy.client.patient.dtos.PatientDTO;
import com.mediscreen.abernathy.client.patient.proxies.AppPatientProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
    public PatientController(@Qualifier("getPatientLogger") Logger logger, AppPatientProxy appPatientProxy) {
        this.logger = logger;
        this.appPatientProxy = appPatientProxy;
    }

    @GetMapping("/patient/list")
    public String getPatientList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            Model model) {

        PagedModel<EntityModel<PatientDTO>> patientCollection = appPatientProxy.getAllPatients(page, size);
        /*List<PatientDTO> patients = patientCollection.getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());*/
        model.addAttribute("allPatients", patientCollection.getContent());
        PagedModel.PageMetadata metadata = patientCollection.getMetadata();
        if (metadata != null) {
            model.addAttribute("pageNb", metadata.getNumber());
            model.addAttribute("pageSize", metadata.getSize());

            if (metadata.getTotalPages() > 1) {
                model.addAttribute("previousPage", patientCollection.getPreviousLink().orElseThrow());
                model.addAttribute("nextPage", patientCollection.getNextLink().orElseThrow());
            }
        }
        return "patient/list";
    }

    @GetMapping("/patient/get")
    public String getPatient(@RequestParam("id") String id, Model model) {
        EntityModel<PatientDTO> patient = appPatientProxy.getPatient(id);
        if (patient == null) {
            model.addAttribute("patientNotFound", true);
            return "patient/list";
        }
        model.addAttribute("patient", patient);
        return "patient/form";
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
            return "patient/list";
        }
        return "patient/form";
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
                return "patient/list";
            } catch (ValidationException e) {
                result.addError(new FieldError("patient", "id", "ID is mandatory."));
                model.addAttribute("patient", patientDTO);
            }
        }
        return "patient/form";
    }

}
