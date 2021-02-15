package com.mediscreen.abernathyapp.assess.models;

public enum DiabeteStatus {

    NONE("None", 3) {
        @Override
        public boolean hasDiabeteStatus(String sex, int age, int terminologyCount) {
            return terminologyCount == 0;
        }
    },
    BORDERLINE("Borderline", 2) {
        @Override
        public boolean hasDiabeteStatus(String sex, int age, int terminologyCount) {
            return age >= 30 && terminologyCount >= 2;
        }
    },
    IN_DANGER("In danger", 1) {
        @Override
        public boolean hasDiabeteStatus(String sex, int age, int terminologyCount) {
            return (sex.equals("M") && age < 30 && terminologyCount >= 3) ||
                    (sex.equals("F") && age < 30 && terminologyCount >= 4) ||
                    (age > 30 && terminologyCount >= 6);
        }
    },
    EARLY_ONSET("Early onset", 0) {
        @Override
        public boolean hasDiabeteStatus(String sex, int age, int terminologyCount) {
            return (sex.equals("M") && age < 30 && terminologyCount >= 5) ||
                    (sex.equals("F") && age < 30 && terminologyCount >= 7) ||
                    (age > 30 && terminologyCount >= 8);
        }
    },
    UNDEFINED("Non défini", Integer.MAX_VALUE) {
        @Override
        public boolean hasDiabeteStatus(String sex, int age, int terminologyCount) {
            return true;
        }
    };

    private final String message;
    private final int evaluationOrder;

    DiabeteStatus(String message, int order) {
        this.message = message;
        this.evaluationOrder = order;
    }

    @Override
    public String toString() {
        return message;
    }

    public int getEvaluationOrder() {
        return evaluationOrder;
    }

    public abstract boolean hasDiabeteStatus(String sex, int age, int terminologyCount);

}
