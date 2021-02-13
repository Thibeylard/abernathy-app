package com.mediscreen.abernathyapp.assess.services;

import java.time.LocalDate;

public interface AgeCalculatorService {

    int getAge(LocalDate dateOfBirth, LocalDate dateToCompare);
}
