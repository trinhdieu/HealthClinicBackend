package com.dungnt.healthclinic.controller;

import com.dungnt.healthclinic.dto.AppointmentRequest;
import com.dungnt.healthclinic.model.Appointment;
import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.service.AppointmentService;
import com.dungnt.healthclinic.service.CalendarService;
import com.dungnt.healthclinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentController {
    private AppointmentService appointmentService;
    private CalendarService calendarService;
    private UserService userService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, CalendarService calendarService,
                                 UserService userService) {
        this.appointmentService = appointmentService;
        this.calendarService = calendarService;
        this.userService = userService;
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.GET)
    public ResponseEntity<Appointment> findAppointmentById(@PathVariable("id") Long id) throws Exception {
        Optional<Appointment> appointment = appointmentService.findById(id);
        if (!appointment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
    }

    /**
     *
     * @param appointmentRequest
     * @return Appointment
     * @throws Exception
     * @description Tao lich hen khi biet cac tham so la Calendar va Client
     */
    @RequestMapping(value = "/appointments", method = RequestMethod.POST)
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequest appointmentRequest) throws Exception {
        Long calendarId = appointmentRequest.getCalendarId();
        Long clientId = appointmentRequest.getClientId();
        Appointment appointment = new Appointment();
        Optional<Calendar> calendar = calendarService.findById(calendarId);
        if (!calendar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        calendarService.checkCalendarState(calendar.get());
        calendar.get().setState(1);
        calendarService.save(calendar.get());
        appointment.setCalendar(calendar.get());

        Optional<User> client = userService.findById(clientId);
        if (!client.isPresent()) {
            calendar.get().setState(0);
            calendarService.save(calendar.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        appointment.setClient(client.get());

        appointmentService.save(appointment);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable("id") Long id) throws Exception {
        Optional<Appointment> appointment = appointmentService.findById(id);
        if (!appointment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Calendar calendar = appointment.get().getCalendar();
        appointmentService.remove(appointment.get());
        LocalDate now = LocalDate.now();
        LocalDate calendarDate = calendar.getDate();
        if (now.isBefore(calendarDate)) {
            calendar.setState(0);
            calendarService.save(calendar);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param clientId
     * @return List
     * @throws Exception
     * @description Tra ve cac cuoc hen cua Client
     */
    @RequestMapping(value = "/getClientAppointments/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getClientAppointments(@PathVariable("id") Long clientId) throws Exception {
        Optional<User> client = userService.findById(clientId);
        if (!client.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Appointment> appointments = appointmentService.findAllByClient(client.get());
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     *
     * @param medicalStaffId
     * @return List
     * @throws Exception
     * @description Tra ve cac cuoc hen cua Medical Staff
     */
    @RequestMapping(value = "/getMedicalStaffAppointments/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getMedicalStaffAppointments(@PathVariable("id") Long medicalStaffId) throws Exception {
        Optional<User> medicalStaff = userService.findById(medicalStaffId);
        if (!medicalStaff.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Calendar> calendars = calendarService.findAllByMedicalStaff(medicalStaff.get());
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Appointment> appointments = new ArrayList<>();
        for (Calendar calendar: calendars) {
            if (calendar.getAppointment() != null) {
                appointments.add(calendar.getAppointment());
            }
        }
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     *
     * @param medicalStaffId
     * @param dateStr
     * @return
     * @throws Exception
     * @description Tra ve cac cuoc hen cua medical staff theo ngay
     */
    @RequestMapping(value = "/getMedicalStaffAppointmentsByDate/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> getMedicalStaffAppointmentsByDate(@PathVariable("id") Long medicalStaffId,
                                                                               @RequestParam("date") String dateStr) throws Exception {
        Optional<User> medicalStaff = userService.findById(medicalStaffId);
        if (!medicalStaff.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LocalDate date = LocalDate.parse(dateStr);
        List<Calendar> calendars = calendarService.findAllByDateAndMedicalStaff(date, medicalStaff.get());
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Appointment> appointments = new ArrayList<>();
        for (Calendar calendar: calendars) {
            if (calendar.getAppointment() != null) {
                appointments.add(calendar.getAppointment());
            }
        }
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     *
     * @param dateStr
     * @return
     * @throws Exception
     * @description Tra ve cac cuoc hen theo ngay
     */
    @RequestMapping(value = "/getAppointmentsByDate", method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam("date") String dateStr) throws Exception {
        LocalDate date = LocalDate.parse(dateStr);
        List<Calendar> calendars = calendarService.findAllByDate(date);
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Appointment> appointments = new ArrayList<>();
        for (Calendar calendar: calendars) {
            if (calendar.getAppointment() != null) {
                appointments.add(calendar.getAppointment());
            }
        }
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }



}
