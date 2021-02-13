package com.mediscreen.abernathyapp.assess.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AgeCalculatorServiceImpl implements AgeCalculatorService {


    @Override
    public int getAge(LocalDate birthDate, LocalDate dateToCompare) {
        int birthYear = birthDate.getYear();
        int birthMonth = birthDate.getMonthValue();
        int birthDay = birthDate.getDayOfMonth();

        int currentYear = dateToCompare.getYear();
        int currentMonth = dateToCompare.getMonthValue();
        int currentDay = dateToCompare.getDayOfMonth();

        int age = currentYear - birthYear;

        if (currentMonth < birthMonth) {
            age -= 1;
        } else if (currentMonth == birthMonth && currentDay < birthDay) {
            age -= 1;
        }

        return age;
    }
}
