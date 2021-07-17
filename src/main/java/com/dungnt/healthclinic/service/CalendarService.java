package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.dto.CalendarRequest;
import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CalendarService {
    List<Calendar> findAll();

    Optional<Calendar> findById(Long id) throws Exception;

    void save(Calendar calendar) throws Exception;

    void remove(Calendar calendar);

    List<Calendar> findSuitableCalendar(CalendarRequest calendarRequest, Integer state) throws Exception;

    List<Calendar> recommendCalendars(CalendarRequest calendarRequest, Integer state) throws Exception;

    void checkCalendarState(Calendar calendar) throws Exception;

    List<Calendar> findAllByDate(LocalDate date) throws Exception;

    List<Calendar> findAllByClinicService(ClinicService service);

    List<Calendar> findAllByClinicServiceAndDate(ClinicService service, LocalDate date);

    List<Calendar> findAllByMedicalStaff(User medicalStaff);

    List<Calendar> findAllByDateAndMedicalStaff(LocalDate date, User medicalStaff);
}
