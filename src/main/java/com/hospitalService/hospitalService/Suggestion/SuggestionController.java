package com.hospitalService.hospitalService.Suggestion;

import com.hospitalService.hospitalService.Doctor.Doctor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/suggestion")
@Api(tags="Suggestion API")
public class SuggestionController {
    private final SuggestionService suggestionService;

    @Autowired
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    @ApiOperation(
            value="Suggests Doctors to the Patients",
            notes="It accepts a request parameter patientId which is the id of patient in database | required:(required)"+"\n"+
                    "It either returns the data of doctor(s) which fulfill the criteria or throws an error an error message" +"\n"+
                    "Edge-Case 1: If there isn’t any doctor on that location (i.e. outside Delhi, Noida, Faridabad), the error message will be “We are still waiting to expand to your location”"+"\n"+
                    "Edge-Case 2: If there isn’t any doctor for that symptom on that location, the error message will be “There isn’t any doctor present at your location for your symptom”"
    )
    public List<Doctor> suggestDoctors(@RequestParam(required = true) Long patientId){
        return suggestionService.suggestDoctors(patientId);
    }
}
