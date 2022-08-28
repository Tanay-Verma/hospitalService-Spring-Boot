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
@RequestMapping("api")
@Api(tags = "Doctor API")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("v1/doctor")
    @ApiOperation(value = "Get Doctors",notes="Gets all Doctors in the Database",tags = {"Doctor API"})
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping("v1/doctor")
    public void registerNewDoctor(@RequestBody Doctor doctor){
        doctorService.addNewDoctor(doctor);
    }

    @DeleteMapping("v1/doctor/{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long doctorId){
        doctorService.deleteDoctor(doctorId);
    }

    @PutMapping("v1/doctor/{doctorId}")
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
