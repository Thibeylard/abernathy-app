package com.mediscreen.abernathy.client.patient.dtos;

import java.util.List;

public class PatientCollectionDTO {

    // _embedded JSON node
    List<PatientItemDTO> patientItems;
    // _links JSON node
    LinksDTO links;
    // page JSON node
    PageDTO page;

    private PatientCollectionDTO() {
    }

    public PatientCollectionDTO(List<PatientItemDTO> patientItems, LinksDTO links, PageDTO page) {
        this.patientItems = patientItems;
        this.links = links;
        this.page = page;
    }

}
