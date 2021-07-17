package com.dungnt.healthclinic.service.impl;

import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.repository.ClinicServiceRepository;
import com.dungnt.healthclinic.service.ClinicSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicSerServiceImpl implements ClinicSerService {
    private ClinicServiceRepository clinicServiceRepository;

    @Autowired
    public ClinicSerServiceImpl(ClinicServiceRepository clinicServiceRepository) {
        this.clinicServiceRepository = clinicServiceRepository;
    }


    @Override
    public List<ClinicService> findAll() {
        return (List<ClinicService>) clinicServiceRepository.findAll();
    }

    @Override
    public Optional<ClinicService> findById(Long id) throws Exception{
        if (id == null) {
            throw new Exception("Gia tri id null");
        }
        return clinicServiceRepository.findById(id);
    }

    @Override
    public void save(ClinicService clinicService) throws Exception {
        if (clinicService == null) {
            throw new Exception("Doi tuong dich vu kham null");
        }
        if (clinicService.getName() == null || clinicService.getName() == "") {
            throw new Exception("Ten dich vu kham null");
        }
//        if (clinicService.getRoom() == null || clinicService.getRoom() == "") {
//            throw new Exception("Ten phong null");
//        }
        clinicServiceRepository.save(clinicService);
    }

    @Override
    public void remove(ClinicService clinicService) {
        clinicServiceRepository.delete(clinicService);
    }

//    @Override
//    public ClinicService findByRoom(String room) throws Exception {
//        if (room == null) {
//            throw new Exception("Gia tri room null");
//        }
//        return clinicServiceRepository.findByRoom(room);
//    }

}
