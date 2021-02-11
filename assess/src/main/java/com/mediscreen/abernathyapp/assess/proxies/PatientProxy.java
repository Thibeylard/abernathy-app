package com.mediscreen.abernathyapp.assess.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "patient")
@RibbonClient(name = "patient")
public interface PatientProxy {

    @GetMapping("/patient/{id}")
    ResponseEntity<?> getPatient(@PathVariable("patientId") String patientId);

    @GetMapping("/patient/search/findByFamilyAndGiven")
    ResponseEntity<?> getPatient(@RequestParam("family") String family,
                                 @RequestParam("given") String given);
}
