package com.mediscreen.abernathy.client.patient.dtos;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class LinksDTO {

    ArrayNode links;

    private LinksDTO() {
    }

    public LinksDTO(ArrayNode links) {
        this.links = links;
    }

    public ArrayNode getLinks() {
        return links;
    }
}
