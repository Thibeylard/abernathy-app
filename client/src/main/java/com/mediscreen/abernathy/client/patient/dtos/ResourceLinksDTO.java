package com.mediscreen.abernathy.client.patient.dtos;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResourceLinksDTO {

    ObjectNode links;

    private ResourceLinksDTO() {
    }

    public ResourceLinksDTO(ObjectNode links) {
        this.links = links;
    }

    public ObjectNode getJsonLinks() {
        return links;
    }

    public String getSelfUrl() {
        return links.get("self").get("href").asText();
    }

}
