package com.mediscreen.abernathy.client.patient.dtos;

import java.util.List;

public class PatientCollectionResourceDTO {

    // _embedded JSON node
    List<PatientItemResourceDTO> patientItems;
    // _links JSON node
    ResourceLinksDTO links;
    // page JSON node
    ResourcePageDTO page;

    private PatientCollectionResourceDTO() {
    }

    public PatientCollectionResourceDTO(List<PatientItemResourceDTO> patientItems, ResourceLinksDTO links, ResourcePageDTO page) {
        this.patientItems = patientItems;
        this.links = links;
        this.page = page;
    }

    public List<PatientItemResourceDTO> getPatientItems() {
        return patientItems;
    }
}
