// This is the Data Access Layer
package com.hospitalService.hospitalService.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

//    This lets us search Doctors my email for email validation
    @Query("SELECT s FROM Doctor s WHERE s.email = ?1")
    Optional<Doctor> findDoctorByEmail(String email);
//    This lets us search Doctors my city for the suggestion api
    @Query("SELECT s FROM Doctor s WHERE s.city = ?1")
    Optional<Doctor> findDoctorByCity(String city);
}


