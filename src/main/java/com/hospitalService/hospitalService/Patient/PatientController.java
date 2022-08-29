// This is the API layer
package com.hospitalService.hospitalService.Patient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/patient")
@Api(tags="Patient API")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @ApiOperation(
            value = "Get Patients",
            notes="Gets all Patients in the Database",
            tags = {"Patient API"})
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    @PostMapping
    @ApiOperation(
            value="Add Patients",
            notes="Adds patient to the database. Accepts a JSON."+"\n"+
                    "IMPORTANT NOTE::"+"\n"+
                    "In the example given below do not supply any \"id\" as it is auto generated, you can either not supply it at all or send it with value 0.",
            tags={"Patient API"})
    public void registerNewPatient(@RequestBody Patient patient){
        patientService.addNewPatient(patient);
    }

    @DeleteMapping(path = "{patientId}")
    @ApiOperation(
            value="Remove Patients",
            notes="Removes patients from database.It requires path variable patientId which is the id of patient in the database",
            tags={"Patient API"})
    public void deletePatient(@PathVariable("patientId") Long patientId){
        patientService.deletePatient(patientId);
    }

    @PutMapping(path = "{patientId}")
    @ApiOperation(
            value="Update Patients",
            notes="Updates the data row of patients in database. It requires: " + "\n"+
                    "=> path variable patientId " + "\n" +
                    "=> request parameter name of Type:String | required:(not required) " + "\n" +
                    "=> request parameter city of Type:String | required:(not required) " + "\n" +
                    "=> request parameter email of Type:String | required:(not required) " + "\n" +
                    "=> request parameter phoneNumber of Type:String | required:(not required) " + "\n" +
                    "=> request parameter symptom of Type:String whose value can be one of [ \"Arthritis\", \"Backpain\", \"Tissue injuries\", \"Dysmenorrhea\", \"Skin infection\", \"Skin burn\", \"Ear pain\"] | required:(not required) " + "\n")
    public void updatePatient(
            @PathVariable("patientId") Long patientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String symptom
    ){
        patientService.updatePatient(patientId,name,city,email,phoneNumber,symptom);
    }
}
