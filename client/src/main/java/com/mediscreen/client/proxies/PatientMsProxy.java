package com.mediscreen.client.proxies;

import com.mediscreen.patientms.dtos.PatientDTO;
import com.mediscreen.patientms.models.Patient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen-zuul-server")
@RibbonClient(name = "mediscreen-patient-ms")
public interface PatientMsProxy {

    @GetMapping("/mediscreen-patient-ms/patient")
    @ResponseBody List<Patient> getAllPatients();

    @GetMapping("/mediscreen-patient-ms/patient/{id}")
    @ResponseBody List<Patient> getPatient(@PathVariable("id") String id);

    @PostMapping("/mediscreen-patient-ms/patient")
    @ResponseBody Patient addPatient(@RequestBody PatientDTO patientDTO);
}
