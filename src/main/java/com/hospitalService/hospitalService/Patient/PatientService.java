// This is the Service Layer
package com.hospitalService.hospitalService.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

//    For adding new Patients
    public void addNewPatient(Patient patient) {
        //        Name Validation Logic
        if(patient.getName().trim().length() < 3){
            throw new IllegalStateException("Name should have at least 3 characters");
        }

//        City validation Logic
        if(patient.getCity().length() > 20){
            throw new IllegalStateException("City name can have only 20 characters at max");
        }

        if(patient.getCity().length() == 0){
            throw new IllegalStateException("Please provide a city");
        }

//        Email Validation Logic

//        checking if the user has provided an email or not
        if(patient.getEmail().isBlank()){
            throw new IllegalStateException("Please provide an email");
        }

//        regex for checking if email entered is correct
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if(!pat.matcher(patient.getEmail()).matches()){
            throw new IllegalStateException("Please enter a valid email");
        }

//        checking if the email provided is already present in the database or not
        Optional<Patient> patientOptional =
                patientRepository.findPatientByEmail(patient.getEmail());
        if(patientOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }

//        Phone number validation Logic
        if(patient.getPhoneNumber().trim().length() < 10){
            throw new IllegalStateException("Enter at least 10 digits");
        }

//        validates phone numbers having 10 digits (9998887776)
        if (patient.getPhoneNumber().matches("\\d{10}")){}

//        validates phone numbers having digits, -, . or spaces
        else if (patient.getPhoneNumber().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")){}
        else if (patient.getPhoneNumber().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")){}

//        validates phone numbers having digits and extension (length 3 to 5)
        else if (patient.getPhoneNumber().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")){}

//        validates phone numbers having digits and area code in braces
        else if (patient.getPhoneNumber().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")){}
        else if (patient.getPhoneNumber().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")){}
        else if (patient.getPhoneNumber().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){}

//        throw error if any of the input matches if not found
        else{
            throw new IllegalStateException("Not a valid phone number");
        }

//        Symptom Validation Logic
        String[] allowedSymptom = new String[]{"arthritis", "backpain", "tissue injuries", "gynecology", "dysmenorrhea", "skin infection", "skin burn","ear pain"};
        boolean isValid = false;
        for(String symptom:allowedSymptom){
            System.out.println("Patient symptom: "+patient.getSymptom().trim().toLowerCase()+"\n"+"Symptom: "+symptom);
            if(patient.getSymptom().trim().toLowerCase().equals(symptom)){
                isValid = true;
                break;
            }
        }
        if(!isValid){
            throw new IllegalStateException("Not a valid symptom");
        }

//        Saving to the database if all checks pass
        patientRepository.save(patient);
    }

//    For deleting patients from database
    public void deletePatient(Long patientId) {
        if(!patientRepository.existsById(patientId)){
            throw new IllegalStateException("patient with id " +patientId+ " does not exists");
        }
        patientRepository.deleteById(patientId);
    }

//    For updating patients in database
    @Transactional
    public void updatePatient(Long patientId, String name, String city, String email, String phoneNumber, String symptom) {
//        Checking if patient is in database or not
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new IllegalStateException("Patient with id " + patientId + " does not exists"));

//        Checking if the new name provided is there and if it is different from current name
        if (name != null && name.length() >= 3 && !Objects.equals(patient.getName(), name)) {
            patient.setName(name);
        }

//        Checking if incoming city is there and is different from current city
        if (city != null && city.length() > 0 && city.length() <= 20 && !Objects.equals(patient.getCity(), city)){
            patient.setCity(city);
        }

//        Checking if email is there in params and also if it is different from the current email
        if (email != null && email.length() > 0 && !Objects.equals(patient.getEmail(), email)) {
//            Checking if the email is correct
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (!pat.matcher(email).matches()) {
                throw new IllegalStateException("Please enter a valid email");
            }
//            Checking if the new email is available or not
            Optional<Patient> patientOptional = patientRepository.findPatientByEmail(email);
            if (patientOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            patient.setEmail(email);
        }
//        Checking the incoming phone number
        if (phoneNumber != null && phoneNumber.length() >= 10 && !Objects.equals(patient.getPhoneNumber(), phoneNumber)) {
//            validates phone numbers having 10 digits (9998887776)
            if (phoneNumber.matches("\\d{10}")) {
            }

//            validates phone numbers having digits, -, . or spaces
            else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            } else if (phoneNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            }

//            validates phone numbers having digits and extension (length 3 to 5)
            else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            }

//            validates phone numbers having digits and area code in braces
            else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            } else if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            } else if (phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            }

//            throw error if any of the input matches is not found
            else {
                throw new IllegalStateException("Not a valid phone number");
            }
            patient.setPhoneNumber(phoneNumber);
        }

//        Checking incoming symptom
        if(symptom != null && symptom.length() > 0 && !Objects.equals(patient.getSymptom(), symptom)){
            String[] allowedSymptom = new String[]{"arthritis", "backpain", "tissue injuries", "gynecology", "dysmenorrhea ", "skin infection", "skin burn","ear pain"};
            boolean isValid = false;
            for(String ele:allowedSymptom){
                if(patient.getSymptom().trim().toLowerCase().equals(ele)){
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                throw new IllegalStateException("Not a valid symptom");
            }
            patient.setSymptom(symptom);
        }

    }
}
