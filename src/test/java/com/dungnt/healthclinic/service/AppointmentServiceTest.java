package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.Appointment;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.repository.AppointmentRepository;
import com.dungnt.healthclinic.repository.CalendarRepository;
import com.dungnt.healthclinic.service.impl.AppointmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentServiceTest {
    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    CalendarRepository calendarRepository;

    @InjectMocks
    AppointmentService appointmentService;

    @Before
    public void setUp() {
        appointmentRepository = mock(AppointmentRepository.class);
        calendarRepository = mock(CalendarRepository.class);
        appointmentService = new AppointmentServiceImpl(appointmentRepository, calendarRepository);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Long id = null;
        try {
            appointmentService.findById(id);
        } catch (Exception e) {
            assertEquals("Gia tri id null", e.getMessage());
        }
    }

    @Test
    public void testFindByIdWhenTrue() throws Exception {
        Long id = 1L;
        Appointment appointment = new Appointment();
        Optional<Appointment> optionalAppointment = Optional.of(appointment);
        when(appointmentRepository.findById(id)).thenReturn(optionalAppointment);
        Optional<Appointment> appointmentTest = appointmentService.findById(id);
        assertNotNull(appointmentTest);
    }

//    @Test
//    public void testFindAllByClientWhenInputNull() {
//        User client = null;
//        try {
//            appointmentService.findAllByClient(client);
//        } catch (Exception e) {
//            assertEquals("", e.getMessage());
//        }
//    }

    @Test
    public void testFindAllByClientWhenTrue() {
        User client = new User();
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentRepository.findAllByClient(client)).thenReturn(appointments);
        List<Appointment> testAppointments = appointmentService.findAllByClient(client);
        assertNotNull(testAppointments);
    }

//    @Test
//    public void testFindAllByMedicalStaffWhenInputNull() {
//        User medicalStaff = null;
//        try {
//            appointmentService.findAllByMedicalStaff(medicalStaff);
//        } catch (Exception e) {
//            assertEquals("", e.getMessage());
//        }
//    }

//    @Test
//    public void testFindAllByMedicalStaffWhenTrue() {
//        User medicalStaff = new User();
//        List<Appointment> appointments = new ArrayList<>();
//        when(appointmentRepository.findAllByMedicalStaff(medicalStaff)).thenReturn(appointments);
//        List<Appointment> testAppointments = appointmentService.findAllByMedicalStaff(medicalStaff);
//        assertNotNull(testAppointments);
//    }

}