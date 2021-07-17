package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.ClinicService;

import java.util.List;
import java.util.Optional;

public interface ClinicSerService {
    List<ClinicService> findAll();

    Optional<ClinicService> findById(Long id) throws Exception;

    void save(ClinicService clinicService) throws Exception;

    void remove(ClinicService clinicService);

//    ClinicService findByRoom(String room) throws Exception;

}
