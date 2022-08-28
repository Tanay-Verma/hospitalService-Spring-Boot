// This is the API Layer
package com.hospitalService.hospitalService.Doctor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("api/v1/doctor")
@Api(tags = "Doctor API")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @ApiOperation(
            value = "Get Doctors",
            notes="Gets all Doctors in the Database",
            tags = {"Doctor API"})
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping
    @ApiOperation(
            value="Add Doctors",
            notes="Adds doctor to the database. Accepts a JSON. In the example given below do not supply any \"id\" as it is auto generated, you can either not supply it at all or send it with value 0.",
            tags={"Doctor API"})
    public void registerNewDoctor(@RequestBody Doctor doctor){
        doctorService.addNewDoctor(doctor);
    }

    @DeleteMapping("{doctorId}")
    @ApiOperation(
            value="Remove Doctors",
            notes="Removes doctors from database.It requires path variable doctorId which is the id of doctor in the database",
            tags={"Doctor API"})
    public void deleteDoctor(@PathVariable("doctorId") Long doctorId){
        doctorService.deleteDoctor(doctorId);
    }

    @PutMapping("{doctorId}")
    @ApiOperation(
            value="Update Doctors",
            notes="Updates the data row of doctors in database. It requires: " + "\n"+
                    "=> path variable doctorId " + "\n" +
                    "=> request parameter name of Type:String | required:(not required) " + "\n" +
                    "=> request parameter city of Type:String whose value can be one of [\"Delhi\",\"Noida\",\"Faridabad\"] | required:(not required) " + "\n" +
                    "=> request parameter email of Type:String | required:(not required) " + "\n" +
                    "=> request parameter phoneNumber of Type:String | required:(not required) " + "\n" +
                    "=> request parameter speciality of Type:String whose value can be one of [ \"Orthopedic\", \"Gynecology\", \"Dermatology\", \"ENT\"]| required:(not required) " + "\n"
    )
    public void updateDoctor(
            @PathVariable("doctorId") Long doctorId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String speciality
    )
    {
        doctorService.updateDoctor(doctorId,name,city,email,phoneNumber,speciality);
    }
}
