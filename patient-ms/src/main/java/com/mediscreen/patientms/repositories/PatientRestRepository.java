package com.mediscreen.patientms.repositories;

import com.mediscreen.patientms.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "patients", path = "patients")
public interface PatientRestRepository extends MongoRepository<Patient, Integer> {
}
