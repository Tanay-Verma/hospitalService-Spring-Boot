package com.hospitalService.hospitalService.Suggestion;

import com.hospitalService.hospitalService.Doctor.Doctor;
import com.hospitalService.hospitalService.Doctor.DoctorController;
import com.hospitalService.hospitalService.Patient.PatientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/suggestion")
public class SuggestionController {
    private final DoctorController doctorController;
    private final PatientController patientController;
    private final SuggestionService suggestionService;

    @Autowired
    public SuggestionController(DoctorController doctorController, PatientController patientController, SuggestionService suggestionService) {
        this.doctorController = doctorController;
        this.patientController = patientController;
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public List<Doctor> suggestDoctors(@RequestParam(required = true) Long patientId){
        return suggestionService.suggestDoctors(patientId);
    }
}
