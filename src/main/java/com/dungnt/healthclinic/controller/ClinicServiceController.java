package com.dungnt.healthclinic.controller;

import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.service.ClinicSerService;
import com.dungnt.healthclinic.service.impl.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class ClinicServiceController {
    private ClinicSerService clinicSerService;
    private ValidationService validationService;

    @Autowired
    public ClinicServiceController(ClinicSerService clinicSerService, ValidationService validationService) {
        this.clinicSerService = clinicSerService;
        this.validationService = validationService;
    }

    @RequestMapping(value = "/clinicservices", method = RequestMethod.GET)
    public ResponseEntity<List<ClinicService>> findAllClinicServices() {
        List<ClinicService> clinicServices = clinicSerService.findAll();
        if (clinicServices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clinicServices, HttpStatus.OK);
    }

    @RequestMapping(value = "/clinicservices/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClinicService> findClinicServiceById(@PathVariable("id") Long id) throws Exception {
        Optional<ClinicService> clinicService = clinicSerService.findById(id);
        if (!clinicService.isPresent()) {
            return new ResponseEntity<>(clinicService.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clinicService.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/clinicservices", method = RequestMethod.POST)
    public ResponseEntity<ClinicService> createClinicService(@RequestBody ClinicService clinicService) throws Exception {
        clinicSerService.save(clinicService);
        return new ResponseEntity<>(clinicService, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clinicservices/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClinicService> updateClinicService(@PathVariable("id") Long id,
                                                             @RequestBody ClinicService clinicService) throws Exception {
        Optional<ClinicService> currentClinicService = clinicSerService.findById(id);
        if (!currentClinicService.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentClinicService.get().setName(clinicService.getName());
        currentClinicService.get().setDescription(clinicService.getDescription());
//        validationService.checkRoom(clinicService.getRoom());
//        currentClinicService.get().setRoom(clinicService.getRoom());
        clinicSerService.save(currentClinicService.get());
        return new ResponseEntity<>(currentClinicService.get(), HttpStatus.OK);

    }

    @RequestMapping(value = "/clinicservices/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ClinicService> deleteClinicService(@PathVariable("id") Long id) throws Exception {
        Optional<ClinicService> clinicService = clinicSerService.findById(id);
        if (!clinicService.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clinicSerService.remove(clinicService.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
