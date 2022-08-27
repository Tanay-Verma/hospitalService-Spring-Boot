// This is the API Layer
package com.hospitalService.hospitalService.Doctor;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping
    public void registerNewDoctor(@RequestBody Doctor doctor){
        doctorService.addNewDoctor(doctor);
    }

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long doctorId){
        doctorService.deleteDoctor(doctorId);
    }

    @PutMapping(path = "{doctorId}")
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
