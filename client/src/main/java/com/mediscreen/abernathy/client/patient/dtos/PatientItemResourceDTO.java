package com.mediscreen.abernathy.client.patient.dtos;

public class PatientItemResourceDTO {

    private PatientDTO item;
    private ResourceLinksDTO links;

    private PatientItemResourceDTO() {
    }

    public PatientItemResourceDTO(PatientDTO item, ResourceLinksDTO links) {
        this.item = item;
        this.links = links;
    }

    public PatientDTO getItem() {
        return item;
    }

    public ResourceLinksDTO getLinks() {
        return links;
    }
}
