package com.mediscreen.client.proxies;

import com.mediscreen.common.dtos.PatientDTO;
import com.mediscreen.patientms.models.Patient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "mediscreen-patient-ms")
public interface PatientMsProxy {

    @GetMapping("/mediscreen-patient-ms/patient")
    @ResponseBody List<Patient> getAllPatients();

    @GetMapping("/mediscreen-patient-ms/patient/{id}")
    @ResponseBody List<Patient> getPatient(@PathVariable("id") String id);

    @PostMapping("/mediscreen-patient-ms/patient")
    @ResponseBody ResponseEntity<?> addPatient(@RequestBody PatientDTO patientDTO);
}
