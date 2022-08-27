package com.hospitalService.hospitalService.Patient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PatientConfig {

    @Bean
    CommandLineRunner patientCommandLineRunnerLineRunner(PatientRepository repository){
        return args -> {
            Patient surender = new Patient(
                    "Surender",
                    "Delhi",
                    "surender@gmail.com",
                    "9876543210",
                    "Ear Pain"
            );
            repository.saveAll(List.of(surender));
        };
    }
}
