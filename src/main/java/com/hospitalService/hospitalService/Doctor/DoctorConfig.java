package com.hospitalService.hospitalService.Doctor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DoctorConfig {

    @Bean
    CommandLineRunner doctorCommandLineRunner(DoctorRepository repository){
        return args -> {
            Doctor vikram = new Doctor(
                    "Vikram",
                    "delhi",
                    "vikram@gmail.com",
                    "9891324430",
                    "ent"
            );
//            To save it on our Database
            repository.saveAll(List.of(vikram));
        };
    }
}
