package com.hospitalService.hospitalService.Doctor;

import javax.persistence.*;

@Entity
@Table
public class Doctor {
    @Id
    @SequenceGenerator(
            name="doctor_sequence",
            sequenceName = "doctor_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "doctor_sequence"
    )
    private Long id;
    private String name;
    private String city;
    private String email;
    private String phoneNumber;
    private String speciality;

    public Doctor() {
    }

    public Doctor(Long id, String name, String city, String email, String phoneNumber, String speciality) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.speciality = speciality;
    }

    public Doctor(String name, String city, String email, String phoneNumber, String speciality) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.speciality = speciality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.trim().toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber + '\'' +
                ", speciality='" + speciality + '\'' +
                '}';
    }
}
