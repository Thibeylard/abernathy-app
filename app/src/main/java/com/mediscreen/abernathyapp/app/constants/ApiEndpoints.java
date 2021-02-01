package com.mediscreen.abernathyapp.app.constants;

public enum ApiEndpoints {

    PATIENT_ADD("/patient/add"),
    PATIENT_UPDATE("/patient/update"),
    PATIENT_GET_ALL("/patient/list"),
    PATIENT_GET_SINGLE("/patient/get");

    private final String uri;

    ApiEndpoints(String uri) {
        this.uri = uri;
    }

    public String uri() {
        return uri;
    }
}
