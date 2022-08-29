package com.hospitalService.hospitalService.Suggestion;

import com.hospitalService.hospitalService.Doctor.Doctor;
import com.hospitalService.hospitalService.Doctor.DoctorRepository;
import com.hospitalService.hospitalService.Patient.Patient;
import com.hospitalService.hospitalService.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SuggestionService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    @Autowired
    public SuggestionService(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> suggestDoctors(Long patientId) {
//        Getting patient data by their id
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        Patient newPatient = (Patient) patientOptional.stream().toArray()[0];

//        Getting the city of the patient
        String city = newPatient.getCity().trim().toLowerCase();

//        Getting all the doctors which are in the same city as patient
        Optional<Doctor> doctorOptional = doctorRepository.findDoctorByCity(city);

//        If no doctor is available in the patient's city
        if(doctorOptional.isEmpty()){
            throw new IllegalStateException("We are still waiting to expand to your location");
        }

//        Mapping speciality with their symptoms in a HashMap
        String[] orthopedic = new String[]{"arthritis", "backpain", "tissue injuries"};
        String[] gynecology = new String[]{"dysmenorrhea"};
        String[] dermatology = new String[]{"skin infection","skin burn"};
        String[] ent = new String[]{"ear pain"};
        HashMap<String, String[]> map = new HashMap<>();
        map.put("orthopedic",orthopedic);
        map.put("gynecology",gynecology);
        map.put("dermatology",dermatology);
        map.put("ent",ent);

//        Getting the patient's symptom
        String symptom = newPatient.getSymptom().trim().toLowerCase();

//        Main suggestion logic
//        looping through the array of doctors in the same city and checking their speciality
//        to see if the patients symptoms match up with doctor's speciality
        for(int i=0;i< doctorOptional.stream().toArray().length;i++){
            Doctor doctor = (Doctor) doctorOptional.stream().toArray()[i];
            String speciality = doctor.getSpeciality().trim().toLowerCase();
            String[] symptomArr = map.get(speciality);
            for(int j=0;j<symptomArr.length;j++){
                if(symptom.equals(symptomArr[i])){
                    return doctorRepository.findById(doctor.getId()).stream().toList();
                }
            }
        }

//        If the doctors are in the same city but do not have speciality needed for the patient's symptom
        throw new IllegalStateException("There isn’t any doctor present at your location for your symptom");
    }
}
