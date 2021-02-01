package com.mediscreen.abernathyapp.app.services;

import com.mediscreen.abernathyapp.app.constants.ApiOperations;
import org.springframework.stereotype.Component;

import static com.mediscreen.abernathyapp.app.constants.ApiEndpoints.*;

@Component
public class MicroserviceURIDispatcher {

    public String getMicroServiceURI(String serviceId, ApiOperations operation) {

        switch (serviceId) {
            case "patient":
                return getPatientURI(operation);
        }

        return null;
    }

    private String getPatientURI(ApiOperations operation) {

        switch (operation) {
            case ADD:
                return PATIENT_ADD.uri();
            case UPDATE:
                return PATIENT_UPDATE.uri();
            case GET_SINGLE:
                return PATIENT_GET_SINGLE.uri();
            case GET_ALL:
                return PATIENT_GET_ALL.uri();
        }

        return null;
    }
}
