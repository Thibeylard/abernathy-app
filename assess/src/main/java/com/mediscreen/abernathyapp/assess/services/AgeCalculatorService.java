package com.mediscreen.abernathyapp.assess.services;

import java.time.LocalDate;

public interface AgeCalculatorService {

    LocalDate getCurrentDate();

    int getCurrentDateYear();

    int getCurrentDateMonth();

    int getCurrentDateDay();

    int getAge(LocalDate dateOfBirth);
}
