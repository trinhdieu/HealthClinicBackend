package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.repository.ClinicServiceRepository;
import com.dungnt.healthclinic.service.impl.ClinicSerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClinicSerServiceTest {
    @Mock
    ClinicServiceRepository clinicServiceRepository;

    @InjectMocks
    ClinicSerService clinicSerService;

    @Before
    public void setUp() {
        clinicServiceRepository = mock(ClinicServiceRepository.class);
        clinicSerService = new ClinicSerServiceImpl(clinicServiceRepository);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Long id = null;
        try {
            clinicSerService.findById(id);
        } catch (Exception e) {
            assertEquals("Gia tri id null", e.getMessage());
        }
    }

    @Test
    public void testFindByIdWhenTrue() throws Exception {
        Long id = 1L;
        ClinicService clinicService = new ClinicService();
        Optional<ClinicService> optionalClinicService = Optional.of(clinicService);
        when(clinicServiceRepository.findById(id)).thenReturn(optionalClinicService);
        Optional<ClinicService> clinicServiceTest = clinicSerService.findById(id);
        assertNotNull(clinicServiceTest);
    }

    @Test
    public void testSaveWhenServiceIsNull() {
        ClinicService clinicService = null;
        try {
            clinicSerService.save(clinicService);
        } catch (Exception e) {
            assertEquals("Doi tuong dich vu kham null", e.getMessage());
        }
    }

    @Test
    public void testSaveWhenServiceNameIsNull() {
        ClinicService clinicService = new ClinicService();
        try {
            clinicSerService.save(clinicService);
        } catch (Exception e) {
            assertEquals("Ten dich vu kham null", e.getMessage());
        }
    }


}