package com.mediscreen.abernathyapp.patient.repositories;

import com.mediscreen.abernathyapp.patient.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "patient", collectionResourceRel = "patient")
public interface PatientRepository extends MongoRepository<Patient, String> {
}