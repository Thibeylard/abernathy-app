package com.mediscreen.patientms.repositories;

import com.mediscreen.patientms.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@RepositoryRestResource(path = "patient", collectionResourceRel = "patient")
public interface PatientRepository extends MongoRepository<Patient, String> {
}