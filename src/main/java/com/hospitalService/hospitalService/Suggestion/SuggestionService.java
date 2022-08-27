package com.hospitalService.hospitalService.Suggestion;

import com.hospitalService.hospitalService.Doctor.Doctor;
import com.hospitalService.hospitalService.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SuggestionService {
    private final PatientRepository patientRepository;
    @Autowired
    public SuggestionService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Doctor> suggestDoctors(Long patientId) {
        
    }
}
