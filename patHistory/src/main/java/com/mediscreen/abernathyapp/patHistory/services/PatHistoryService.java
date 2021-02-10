package com.mediscreen.abernathyapp.patHistory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PatHistoryService {

    int terminologySearch(String patientId, Set<String> terminology);

}
