package com.mediscreen.abernathyapp.app.constants;

public enum ApiExposedOperations {
    ADD("/add"),
    UPDATE("/update"),
    GET_ALL("/list"),
    GET_SINGLE("/get");

    private String uri;

    ApiExposedOperations(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
