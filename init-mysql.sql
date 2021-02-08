/*
    MySQL
    Description : Initialize database abernathy for note microservice
 */
 CREATE DATABASE IF NOT EXISTS abernathy;
 USE abernathy;

CREATE TABLE IF NOT EXISTS patient (
  id VARCHAR(80) PRIMARY KEY,
  family VARCHAR(30) NOT NULL,
  given VARCHAR(30) NOT NULL,
  dob DATE NOT NULL,
  sex CHAR (1) NOT NULL,
  address VARCHAR(50) NOT NULL,
  phone VARCHAR(11) NOT NULL
);
