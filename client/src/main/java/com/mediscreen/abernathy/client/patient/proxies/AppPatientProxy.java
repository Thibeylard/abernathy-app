package com.mediscreen.abernathy.client.patient.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "app")
@RibbonClient(name = "app")
public interface AppPatientProxy {

    // TODO handle sort parameter
    // Because hal+json, return string which is converted in controller
    @GetMapping("/patient/list")
    String getAllPatients(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
//            @RequestParam(name="sort", required = false) String sort
    );

    @GetMapping("/patient/get")
    String getPatient(@RequestParam("id") String id);

    @PostMapping("/patient/add")
    String addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    @PostMapping("/patient/update")
    String updatePatient(
            @RequestParam("id") String id,
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);
}
