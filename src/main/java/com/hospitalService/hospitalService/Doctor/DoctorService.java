// This is the Service Layer where Business Logic goes
package com.hospitalService.hospitalService.Doctor;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctors(){
        // this returns a list to us
        return doctorRepository.findAll();
    }

//    For adding new Doctors
    public void addNewDoctor(Doctor doctor) {
//        Name Validation Logic
        if(doctor.getName().trim().length() < 3){
            throw new IllegalStateException("Name should have at least 3 characters");
        }

//        City validation Logic
        if(doctor.getCity().length() > 20){
            throw new IllegalStateException("City name can have only 20 characters at max");
        }
        String[] allowedCities = new String[]{"delhi","noida","faridabad"};
        boolean isValid = false;
        for(String city:allowedCities){
            if(doctor.getCity().toLowerCase().trim().equals(city)){
                isValid = true;
                break;
            }
        }
        if(!isValid){
            throw new IllegalStateException("Not a valid location");
        }

//        Email Validation Logic

//        checking if the user has provided an email or not
        if(doctor.getEmail().isBlank()){
            throw new IllegalStateException("Please provide an email");
        }

//        regex for checking if email entered is correct
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if(!pat.matcher(doctor.getEmail()).matches()){
            throw new IllegalStateException("Please enter a valid email");
        }

//        checking if the email provided is already present in the database or not
        Optional<Doctor> doctorOptional =
                doctorRepository.findDoctorByEmail(doctor.getEmail());
        if(doctorOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }

//        Phone number validation Logic
        if(doctor.getPhoneNumber().trim().length() < 10){
            throw new IllegalStateException("Enter at least 10 digits");
        }


//        validates phone numbers having 10 digits (9998887776)
        if (doctor.getPhoneNumber().matches("\\d{10}")){}

//        validates phone numbers having digits, -, . or spaces
        else if (doctor.getPhoneNumber().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")){}
        else if (doctor.getPhoneNumber().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")){}

//        validates phone numbers having digits and extension (length 3 to 5)
        else if (doctor.getPhoneNumber().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")){}

//        validates phone numbers having digits and area code in braces
        else if (doctor.getPhoneNumber().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")){}
        else if (doctor.getPhoneNumber().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")){}
        else if (doctor.getPhoneNumber().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){}

//        throw error if any of the input matches if not found
        else{
            throw new IllegalStateException("Not a valid phone number");
        }

//        Speciality Validation Logic
        String[] allowedSpeciality = new String[]{"orthopedic", "gynecology", "dermatology", "ent"};
        isValid = false;
        for(String speciality:allowedSpeciality){
            if(doctor.getSpeciality().trim().toLowerCase().equals( speciality)){
                isValid = true;
                break;
            }
        }
        if(!isValid){
            throw new IllegalStateException("Not a valid speciality");
        }

//        Saving to the database if all checks pass
        doctorRepository.save(doctor);
    }

//    To delete Doctors from database
    public void deleteDoctor(Long doctorId) {
        if(!doctorRepository.existsById(doctorId)){
            throw new IllegalStateException("doctor with id "+doctorId+" does not exists");
        }
        doctorRepository.deleteById(doctorId);
    }

//    To make changes to existing data of Doctor in database
    @Transactional
    public void updateDoctor(Long doctorId, String name, String city, String email, String phoneNumber, String speciality) {
//        Checking if doctor is in database or not
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalStateException("Doctor with id " + doctorId + " does not exists"));

//        Checking if the new name provided is there and if it is different from current name
        if (name != null && name.length() >= 3 && !Objects.equals(doctor.getName(), name)) {
            doctor.setName(name);
        }

//        Checking if the incoming city is valid or not
        boolean isValid = false;
        if (city != null && city.length() > 0 && city.length() <= 20 && !Objects.equals(doctor.getCity(), city)) {
            String[] allowedCities = new String[]{"delhi", "noida", "faridabad"};
            for (String ele : allowedCities) {
                if (city.toLowerCase().trim().equals(ele)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                throw new IllegalStateException("Not a valid location");
            }
            doctor.setCity(city);
        }

//        Checking if email is there in params and also if it is different from the current email
        if (email != null && email.length() > 0 && !Objects.equals(doctor.getEmail(), email)) {
//        Checking if the email is correct
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (!pat.matcher(email).matches()) {
                throw new IllegalStateException("Please enter a valid email");
            }
//            Checking if the new email is available or not
            Optional<Doctor> doctorOptional = doctorRepository.findDoctorByEmail(email);
            if (doctorOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            doctor.setEmail(email);
        }

//        Checking the incoming phone number
        if (phoneNumber != null && phoneNumber.length() >= 10 && !Objects.equals(doctor.getPhoneNumber(), phoneNumber)) {
//        validates phone numbers having 10 digits (9998887776)
            if (phoneNumber.matches("\\d{10}")) {
            }

//        validates phone numbers having digits, -, . or spaces
            else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            } else if (phoneNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            }

//        validates phone numbers having digits and extension (length 3 to 5)
            else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            }

//        validates phone numbers having digits and area code in braces
            else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            } else if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            } else if (phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            }

//        throw error if any of the input matches is not found
            else {
                throw new IllegalStateException("Not a valid phone number");
            }
            doctor.setPhoneNumber(phoneNumber);
        }

//        Checking the incoming speciality
        isValid = false;
        if (speciality != null && speciality.length() > 0 && !Objects.equals(doctor.getSpeciality(), speciality)) {
            String[] allowedSpeciality = new String[]{"orthopedic", "gynecology", "dermatology", "ent"};
            for (String ele : allowedSpeciality) {
                if (speciality.trim().toLowerCase().equals(ele)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                throw new IllegalStateException("Not a valid speciality");
            }
            doctor.setSpeciality(speciality);
        }
    }
}
