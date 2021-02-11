package com.mediscreen.abernathyapp.patHistory.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PatHistoryService {

    long terminologySearch(String patientId, Set<String> terminology);

}
