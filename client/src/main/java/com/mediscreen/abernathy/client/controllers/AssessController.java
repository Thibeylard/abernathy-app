package com.mediscreen.abernathy.client.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathy.client.proxies.AppProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AssessController {


    private final Logger logger;
    private final AppProxy appProxy;
    private final ObjectMapper mapper;

    @Autowired
    public AssessController(Logger logger,
                            AppProxy appProxy,
                            ObjectMapper mapper) {
        this.logger = logger;
        this.appProxy = appProxy;
        this.mapper = mapper;
    }


    @GetMapping(value = "/assess/id")
    public String assessPatientById(@RequestParam("patId") String patId,
                                    RedirectAttributes redirectAttributes) throws JsonProcessingException {
        ResponseEntity<String> diabeteAssessment = appProxy.getDiabeteAssessmentByPatId(patId);
        if (diabeteAssessment.getStatusCode() == HttpStatus.OK) {
            String assessment = assessPatient(diabeteAssessment);
            redirectAttributes.addFlashAttribute("patientAssessment", assessment);
        } else {
            redirectAttributes.addFlashAttribute("assessmentFailed", true);
        }

        return "redirect:/patient/list";
    }

    @GetMapping(value = "/assess/familyGiven")
    public String assessPatientByName(@RequestParam("familyName") String familyName,
                                      @RequestParam("givenName") String givenName,
                                      RedirectAttributes redirectAttributes) throws JsonProcessingException {
        ResponseEntity<String> diabeteAssessment = appProxy.getDiabeteAssessmentByName(familyName, givenName);
        if (diabeteAssessment.getStatusCode() == HttpStatus.OK) {
            String assessment = assessPatient(diabeteAssessment);
            redirectAttributes.addFlashAttribute("patientAssessment", assessment);
        } else {
            redirectAttributes.addFlashAttribute("assessmentFailed", true);
        }

        return "redirect:/patient/list";
    }

    private String assessPatient(ResponseEntity<String> diabeteAssessment) throws JsonProcessingException {

        DiabeteAssessmentDTO diabeteAssessmentDTO = mapper.readValue(
                diabeteAssessment.getBody(),
                DiabeteAssessmentDTO.class);
        StringBuilder assessment = new StringBuilder();
        assessment.append("Patient: ")
                .append(diabeteAssessmentDTO.getPatient().getFamily())
                .append(" ")
                .append(diabeteAssessmentDTO.getPatient().getGiven())
                .append(" (age ").append(diabeteAssessmentDTO.getPatient().getAge()).append(")")
                .append(" est en statut diabète : ")
                .append(diabeteAssessmentDTO.getAssessment());
        return assessment.toString();
    }
}
