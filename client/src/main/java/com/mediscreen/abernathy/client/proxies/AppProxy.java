package com.mediscreen.abernathy.client.proxies;

import com.mediscreen.abernathy.client.dtos.PatHistoryDTO;
import com.mediscreen.abernathy.client.dtos.PatientDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "app")
@RibbonClient(name = "app")
// TODO divide into as much proxy as there are microservices
public interface AppProxy {

    // TODO handle sort parameter
    // Because hal+json, return string which is converted in controller
    @GetMapping("/patient/list")
    PagedModel<EntityModel<PatientDTO>> getAllPatients(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
//            @RequestParam(name="sort", required = false) String sort
    );

    @GetMapping("/patient/get")
    EntityModel<PatientDTO> getPatient(@RequestParam("id") String id);

    @PostMapping("/patient/add")
    EntityModel<PatientDTO> addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    @PutMapping("/patient/update")
    EntityModel<PatientDTO> updatePatient(
            @RequestParam("id") String id,
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    // TODO handle sort parameter
    // Because hal+json, return string which is converted in controller
    @GetMapping("/patHistory/list")
    PagedModel<EntityModel<PatHistoryDTO>> getAllPatHistory(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
//            @RequestParam(name="sort", required = false) String sort
    );

    @GetMapping("/patHistory/get")
    EntityModel<PatHistoryDTO> getPatHistory(@RequestParam("id") String id);

    @PostMapping("/patHistory/add")
    EntityModel<PatHistoryDTO> addPatHistory(
            @RequestParam("patientId") String patientId,
            @RequestParam("content") String content);

    @PutMapping("/patHistory/update")
    EntityModel<PatHistoryDTO> updatePatHistory(
            @RequestParam("id") String id,
            @RequestParam("patientId") String patientId,
            @RequestParam("content") String content);

    @GetMapping("/patHistory/ofPatient")
    CollectionModel<EntityModel<PatHistoryDTO>> getPatientPatHistory(
            @RequestParam(name = "patientId") String patientId
    );

    @GetMapping("/assess/assess/id")
    ResponseEntity<String> getDiabeteAssessmentByPatId(@RequestParam("patId") String patId);

    @GetMapping("/assess/assess/familyGiven")
    ResponseEntity<String> getDiabeteAssessmentByName(@RequestParam("familyName") String familyName,
                                                      @RequestParam("givenName") String givenName);
}
