package com.mediscreen.abernathyapp.assess.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AgeCalculatorServiceImpl implements AgeCalculatorService {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public int getCurrentDateYear() {
        return getCurrentDate().getYear();
    }

    @Override
    public int getCurrentDateMonth() {
        return getCurrentDate().getMonthValue();
    }

    @Override
    public int getCurrentDateDay() {
        return getCurrentDate().getDayOfMonth();
    }

    @Override
    public int getAge(LocalDate birthDate) {
        int birthYear = birthDate.getYear();
        int birthMonth = birthDate.getMonthValue();
        int birthDay = birthDate.getDayOfMonth();

        int currentYear = getCurrentDateYear();
        int currentMonth = getCurrentDateMonth();
        int currentDay = getCurrentDateDay();

        int age = currentYear - birthYear;

        if (currentMonth > birthMonth) {
            age -= 1;
        } else if (currentMonth == birthMonth && currentDay > birthDay) {
            age -= 1;
        }

        return age;
    }
}
