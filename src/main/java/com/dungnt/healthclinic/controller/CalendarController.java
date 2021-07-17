package com.dungnt.healthclinic.controller;

import com.dungnt.healthclinic.dto.CalendarRequest;
import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.service.CalendarService;
import com.dungnt.healthclinic.service.ClinicSerService;
import com.dungnt.healthclinic.service.UserService;
import com.dungnt.healthclinic.service.impl.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CalendarController {
    private ClinicSerService clinicSerService;
    private CalendarService calendarService;
    private UserService userService;
    private ValidationService validationService;

    @Autowired
    public CalendarController(ClinicSerService clinicSerService, CalendarService calendarService, UserService userService,
                              ValidationService validationService) {
        this.clinicSerService = clinicSerService;
        this.calendarService = calendarService;
        this.userService = userService;
        this.validationService = validationService;
    }

    @RequestMapping(value = "/calendars", method = RequestMethod.GET)
    public ResponseEntity<List<Calendar>> findAllCalendars() {
        List<Calendar> calendars = calendarService.findAll();
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }

    @RequestMapping(value = "/calendars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Calendar> findCalendarById(@PathVariable("id") Long id) throws Exception {
        Optional<Calendar> calendar = calendarService.findById(id);
        if (!calendar.isPresent()) {
            return new ResponseEntity<>(calendar.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(calendar.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/calendars", method = RequestMethod.POST)
    public ResponseEntity<Calendar> createCalendar(@RequestParam("clinicServiceId") Long clinicServiceId,
                                                   @RequestParam("medicalStaffId") Long medicalStaffId,
                                                   @RequestBody Calendar calendar) throws Exception {
        Optional<ClinicService> clinicService = clinicSerService.findById(clinicServiceId);
        if (!clinicService.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        calendar.setClinicService(clinicService.get());
        Optional<User> medicalStaff = userService.findById(medicalStaffId);
        if (!medicalStaff.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        calendar.setMedicalStaff(medicalStaff.get());
        validationService.validateCalendar(calendar);
        calendarService.save(calendar);
        return new ResponseEntity<>(calendar, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/calendars/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Calendar> updateCalendar(@PathVariable("id") Long id, @RequestBody Calendar calendar,
                                                   @RequestParam(value = "clinicServiceId", required = false) Long clinicServiceId,
                                                   @RequestParam(value = "medicalStaffId", required = false) Long medicalStaffId) throws Exception {
        Optional<Calendar> currentCalendar = calendarService.findById(id);
        if (!currentCalendar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentCalendar.get().setDate(calendar.getDate());
        currentCalendar.get().setTimeStart(calendar.getTimeStart());
        currentCalendar.get().setTimeEnd(calendar.getTimeEnd());
        currentCalendar.get().setState(calendar.getState());

        if (clinicServiceId != null) {
            Optional<ClinicService> newClinicService = clinicSerService.findById(clinicServiceId);
            if (!newClinicService.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            currentCalendar.get().setClinicService(newClinicService.get());
        }

        if (medicalStaffId != null) {
            Optional<User> newMedicalStaff = userService.findById(medicalStaffId);
            if (!newMedicalStaff.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            currentCalendar.get().setMedicalStaff(newMedicalStaff.get());
        }
        validationService.validateCalendar(currentCalendar.get());
        calendarService.save(currentCalendar.get());
        return new ResponseEntity<>(currentCalendar.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/calendars/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Calendar> deleteCalendar(@PathVariable("id") Long id) throws Exception {
        Optional<Calendar> calendar = calendarService.findById(id);
        if (!calendar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        calendarService.remove(calendar.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param calendarRequest
     * @return List
     * @throws Exception
     * @description Lay ra lich hen mong muon, hoac goi y mot so lich hen neu lich hen mong muon khong the dap ung
     */
    @RequestMapping(value = "/getCalendars", method = RequestMethod.POST)
    public ResponseEntity<List<Calendar>> getSuitableCalendars(@RequestBody CalendarRequest calendarRequest) throws Exception {
        List<Calendar> suitableCalendars = calendarService.findSuitableCalendar(calendarRequest, 0);
        if (suitableCalendars.isEmpty()) {
            List<Calendar> recommendedCalendars = calendarService.recommendCalendars(calendarRequest, 0);
            return new ResponseEntity<>(recommendedCalendars, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(suitableCalendars, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getCalendarsByDate", method = RequestMethod.POST)
    public ResponseEntity<List<Calendar>> getCalendarsByDate(@RequestParam("date") String dateStr) throws Exception {
        LocalDate date = LocalDate.parse(dateStr);
        List<Calendar> calendars = calendarService.findAllByDate(date);
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCalendarsByClinicService", method = RequestMethod.POST)
    public ResponseEntity<List<Calendar>> getCalendarsByClinicService(@RequestParam("id") String clinicServiceIdStr) throws Exception {
        Long clinicServiceId = Long.parseLong(clinicServiceIdStr);
        Optional<ClinicService> service = clinicSerService.findById(clinicServiceId);
        if (service.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Calendar> calendars = calendarService.findAllByClinicService(service.get());
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCalendarsByClinicServiceAndDate", method = RequestMethod.POST)
    public ResponseEntity<List<Calendar>> getCalendarsByClinicServiceAndDate(@RequestParam("id") String clinicServiceIdStr,
                                                                             @RequestParam("date") String dateStr) throws Exception {
        Long clinicServiceId = Long.parseLong(clinicServiceIdStr);
        Optional<ClinicService> service = clinicSerService.findById(clinicServiceId);
        if (service.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LocalDate date = LocalDate.parse(dateStr);
        List<Calendar> calendars = calendarService.findAllByClinicServiceAndDate(service.get(), date);
        if (calendars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }

}
