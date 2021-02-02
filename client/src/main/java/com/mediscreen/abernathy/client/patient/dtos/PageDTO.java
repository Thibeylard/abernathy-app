package com.mediscreen.abernathy.client.patient.dtos;

public class PageDTO {

    private int size;
    private int totalElements;
    private int totalPages;
    private int current;

    private PageDTO() {
    }

    public PageDTO(int size, int totalElements, int totalPages, int current) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrent() {
        return current;
    }
}
