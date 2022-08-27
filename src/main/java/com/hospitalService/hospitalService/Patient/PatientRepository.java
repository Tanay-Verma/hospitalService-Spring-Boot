package com.hospitalService.hospitalService.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

//    This lets us search Doctors my email for email validation
    @Query("SELECT s FROM Patient s WHERE s.email = ?1")
    Optional<Patient> findPatientByEmail(String email);
}

