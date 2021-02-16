package com.mediscreen.abernathyapp.assess.models;

public enum DiabeteTerminology {

    HEMOGLOBIN_A1C("Hémoglobine A1C"),
    MICROALBUMIN("Microalbumine"),
    HEIGHT("Taille"),
    WEIGHT("Poids"),
    SMOKER("Fumeur"),
    ABNORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGO("Vertige"),
    RELAPSE("Rechute"),
    REACTION("Réaction"),
    ANTIBODY("Anticorps");

    private String fr;

    DiabeteTerminology(String fr) {
        this.fr = fr;
    }

    public String fr() {
        return fr;
    }
}
