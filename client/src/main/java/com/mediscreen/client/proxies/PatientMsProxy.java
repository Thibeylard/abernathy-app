package com.mediscreen.client.proxies;

import com.mediscreen.common.dtos.PatientDTO;
import com.mediscreen.patientms.models.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen-patient-ms")
public interface PatientMsProxy {

    @GetMapping("/patient")
    @ResponseBody List<Patient> getAllPatients();

    @GetMapping("/patient/{id}")
    @ResponseBody List<Patient> getPatient(@PathVariable("id") String id);

    @PostMapping("/patient")
    @ResponseBody ResponseEntity<?> addPatient(@RequestBody PatientDTO patientDTO);
}
