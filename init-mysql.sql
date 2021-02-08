/*
    MySQL
    Description : Initialize database abernathy for note microservice
 */
 CREATE DATABASE IF NOT EXISTS abernathy;
 USE abernathy;

CREATE TABLE IF NOT EXISTS note (
  id VARCHAR(80) PRIMARY KEY,
  patient_id VARCHAR(30) NOT NULL,
  content TEXT NOT NULL
);