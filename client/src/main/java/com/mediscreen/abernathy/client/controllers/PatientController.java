package com.mediscreen.abernathy.client.controllers;

import com.mediscreen.abernathy.client.dtos.PatHistoryDTO;
import com.mediscreen.abernathy.client.dtos.PatientDTO;
import com.mediscreen.abernathy.client.proxies.AppProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PatientController {


    private final Logger logger;
    private final AppProxy appProxy;
    // private final AppPatHistoryProxy appPatHistoryProxy;

    @Autowired
    public PatientController(Logger logger,
                             AppProxy appProxy
    ) {
        this.logger = logger;
        this.appProxy = appProxy;
    }

    @GetMapping("/patient/list")
    public String getPatientList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            Model model) {

        PagedModel<EntityModel<PatientDTO>> patientCollection = appProxy.getAllPatients(page, size);

        model.addAttribute("allPatients", patientCollection.getContent());

        // Add Flash attributes as Model attributes -----------------------------------------------------------
        // TODO improve with switch or private method ?
        if (model.asMap().get("patientAdded") != null) {
            model.addAttribute("patientAdded", model.asMap().get("patientAdded"));
        }
        if (model.asMap().get("patientUpdated") != null) {
            model.addAttribute("patientUpdated", model.asMap().get("patientUpdated"));
        }
        if (model.asMap().get("patientNotFound") != null) {
            model.addAttribute("patientNotFound", model.asMap().get("patientNotFound"));
        }
        if (model.asMap().get("patHistoryNotFound") != null) {
            model.addAttribute("patHistoryNotFound", model.asMap().get("patHistoryNotFound"));
        }
        // END Add Flash attributes as Model attributes --------------------------------------------------------

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
    public String getPatient(@RequestParam("id") String id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        EntityModel<PatientDTO> patient = appProxy.getPatient(id);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("patientNotFound", true);
            return "redirect:/patient/list";
        }
        model.addAttribute("patientResource", patient);

        // Add Flash attributes as Model attributes -----------------------------------------------------------
        // TODO improve with switch or private method ?
        if (model.asMap().get("patHistoryAdded") != null) {
            model.addAttribute("patHistoryAdded", model.asMap().get("patHistoryAdded"));
        }
        if (model.asMap().get("patHistoryUpdated") != null) {
            model.addAttribute("patHistoryUpdated", model.asMap().get("patHistoryUpdated"));
        }
        // END Add Flash attributes as Model attributes --------------------------------------------------------

        //TODO handle paging notes
        CollectionModel<EntityModel<PatHistoryDTO>> patHistoryCollection =
                appProxy.getPatientPatHistory(id);
        model.addAttribute("patHistoryItem", patHistoryCollection.getContent());
        model.addAttribute("patHistoryLink", patHistoryCollection.getLinks());
        return "patient/details";
    }

    @GetMapping(value = "/patient/update")
    public String updatePatientForm(@RequestParam("id") String id,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        EntityModel<PatientDTO> patient = appProxy.getPatient(id);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("patientNotFound", true);
            return "redirect:/patient/list";
        }
        model.addAttribute("patientToUpdateID", patient.getContent().getId());
        model.addAttribute("patientToUpdate", patient.getContent());
        return "patient/update";
    }

    @PostMapping(value = "/patient/update")
    public String updatePatientAction(@Valid @ModelAttribute("patientToUpdate") PatientDTO patientDTO,
                                      BindingResult result,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (patientDTO.getId() == null || patientDTO.getId().isBlank()) {
            result.addError(new FieldError("patientToUpdate", "id", "ID is mandatory."));
        }

        if (result.hasErrors()) {
            model.addAttribute("patientToUpdate", patientDTO);
            return "patient/update";
        } else {
            appProxy.updatePatient(
                    patientDTO.getId(),
                    patientDTO.getFamily(),
                    patientDTO.getGiven(),
                    patientDTO.getDob(),
                    patientDTO.getSex(),
                    patientDTO.getAddress(),
                    patientDTO.getPhone()
            );
            redirectAttributes.addFlashAttribute("patientUpdated", true);
        }

        return "redirect:/patient/list";
    }

    @GetMapping(value = "/patient/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patientToAdd", new PatientDTO(null, null, null, null, null, null));
        return "patient/add";
    }

    @PostMapping(value = "/patient/add")
    public String addPatientAction(@Valid @ModelAttribute("patientToAdd") PatientDTO patientDTO,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("patientToAdd", patientDTO);
            return "patient/add";
        } else {
            appProxy.addPatient(
                    patientDTO.getFamily(),
                    patientDTO.getGiven(),
                    patientDTO.getDob(),
                    patientDTO.getSex(),
                    patientDTO.getAddress(),
                    patientDTO.getPhone()
            );
            redirectAttributes.addFlashAttribute("patientAdded", true);
            return "redirect:/patient/list";
        }
    }

}
