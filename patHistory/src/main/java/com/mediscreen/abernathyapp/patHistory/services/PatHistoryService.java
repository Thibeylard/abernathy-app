package com.mediscreen.abernathyapp.patHistory.services;

import com.mediscreen.abernathyapp.patHistory.dtos.PatHistoryTermsCountDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PatHistoryService {

    PatHistoryTermsCountDTO terminologySearch(String patientId, Set<String> terminology);

}
