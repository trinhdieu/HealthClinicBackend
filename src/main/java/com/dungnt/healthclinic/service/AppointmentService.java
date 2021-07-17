package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.Appointment;
import com.dungnt.healthclinic.model.User;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAll();

    Optional<Appointment> findById(Long id) throws Exception;

    void save(Appointment appointment);

    void remove(Appointment appointment);

    List<Appointment> findAllByClient(User client);

//    List<Appointment> findAllByMedicalStaff(User medicalStaff);

}
