package com.mediscreen.abernathy.client.patient.proxies;

import com.mediscreen.abernathy.client.patient.dtos.PatientCollectionDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientItemDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "app")
@RibbonClient(name = "app")
public interface AppPatientProxy {

    // TODO handle sort parameter
    @GetMapping("/patient/list")
    PatientCollectionDTO getAllPatients(
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "size", required = false) int size
//            @RequestParam(name="sort", required = false) String sort
    );

    @GetMapping("/patient/")
    PatientItemDTO getPatient(@RequestParam("id") String id);

    @PostMapping("/patient/add")
    PatientItemDTO addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    @PostMapping("/patient/update")
    PatientItemDTO updatePatient(
            @RequestParam("id") String id,
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);
}
