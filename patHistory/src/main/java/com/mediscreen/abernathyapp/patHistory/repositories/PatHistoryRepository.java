package com.mediscreen.abernathyapp.patHistory.repositories;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "patHistory", collectionResourceRel = "patHistory")
public interface PatHistoryRepository extends MongoRepository<PatHistory, String> {

    @RestResource(path = "withPatientId", rel = "withPatientId")
    List<PatHistory> findByPatientId(String patientId);
}