package com.mediscreen.abernathyapp.assess.models;

public enum DiabeteStatus {

    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In danger"),
    EARLY_ON_SET("Early onset");

    private final String message;

    DiabeteStatus(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
