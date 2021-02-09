package com.mediscreen.abernathyapp.patHistory.repositories;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "patHistory", collectionResourceRel = "patHistory")
public interface PatHistoryRepository extends MongoRepository<PatHistory, String> {

    public List<PatHistory> findByPatientId(String patientId);
}