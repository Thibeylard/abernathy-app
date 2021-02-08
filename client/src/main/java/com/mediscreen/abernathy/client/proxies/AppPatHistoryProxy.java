package com.mediscreen.abernathy.client.proxies;

import com.mediscreen.abernathy.client.dtos.PatHistoryDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "app")
@RibbonClient(name = "app")
public interface AppPatHistoryProxy {

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
}
