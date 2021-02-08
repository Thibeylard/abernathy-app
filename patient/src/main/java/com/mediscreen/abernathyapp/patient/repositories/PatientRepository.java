package com.mediscreen.abernathyapp.patient.repositories;

import com.mediscreen.abernathyapp.patient.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "patient", collectionResourceRel = "patient")
public interface PatientRepository extends JpaRepository<Patient, String> {
}