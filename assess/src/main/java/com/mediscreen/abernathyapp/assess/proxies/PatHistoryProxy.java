package com.mediscreen.abernathyapp.assess.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "patHistory")
@RibbonClient(name = "patHistory")
public interface PatHistoryProxy {

    @GetMapping("/patHistory/countTerms")
    ResponseEntity<?> getAssessment(@RequestParam("patientId") String patientId,
                                    @RequestParam("terminology") Set<String> terminology);
}
