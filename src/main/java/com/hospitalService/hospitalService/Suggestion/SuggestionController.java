package com.hospitalService.hospitalService.Suggestion;

import com.hospitalService.hospitalService.Doctor.Doctor;
import com.hospitalService.hospitalService.Doctor.DoctorController;
import com.hospitalService.hospitalService.Patient.PatientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/suggestion")
public class SuggestionController {
    private final SuggestionService suggestionService;

    @Autowired
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public List<Doctor> suggestDoctors(@RequestParam(required = true) Long patientId){
        return suggestionService.suggestDoctors(patientId);
    }
}
