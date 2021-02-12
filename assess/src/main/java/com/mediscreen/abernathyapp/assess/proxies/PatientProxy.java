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

    @GetMapping("/patient/{id}?projection=healthInfos")
    ResponseEntity<?> getPatient(@PathVariable("id") String id);

    @GetMapping("/patient/search/findByFamilyAndGiven?projection=healthInfos")
    ResponseEntity<?> getPatient(@RequestParam("family") String family,
                                 @RequestParam("given") String given);
}
