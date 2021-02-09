package com.mediscreen.abernathyapp.app.constants;

public enum ApiExposedOperations {
    ADD("/add"),
    UPDATE("/update"),
    GET_ALL("/list"),
    GET_SINGLE("/get"),
    GET_OF_PATIENT("/ofPatient");

    private final String uri;

    ApiExposedOperations(String uri) {
        this.uri = uri;
    }

    public String getBaseUri() {
        return uri;
    }
}
