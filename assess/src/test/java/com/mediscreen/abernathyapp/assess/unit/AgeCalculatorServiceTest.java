package com.mediscreen.abernathyapp.assess.unit;


import com.mediscreen.abernathyapp.assess.services.AgeCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AgeCalculatorServiceTest {

    @Autowired
    private AgeCalculatorService ageCalculatorService;

    @Test
    public void getRightAge() {

        assertThat(ageCalculatorService.getAge(LocalDate.of(1995, 12, 25),
                LocalDate.of(2007, 12, 21)))
                .isEqualTo(11);

        assertThat(ageCalculatorService.getAge(LocalDate.of(1994, 6, 12),
                LocalDate.of(1996, 6, 28)))
                .isEqualTo(2);

        assertThat(ageCalculatorService.getAge(LocalDate.of(1983, 5, 5),
                LocalDate.of(2003, 4, 9)))
                .isEqualTo(19);

        assertThat(ageCalculatorService.getAge(LocalDate.of(1951, 11, 17),
                LocalDate.of(2001, 11, 17)))
                .isEqualTo(50);
    }
}
