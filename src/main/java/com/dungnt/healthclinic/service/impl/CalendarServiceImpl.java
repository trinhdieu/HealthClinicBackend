package com.dungnt.healthclinic.service.impl;

import com.dungnt.healthclinic.dto.CalendarRequest;
import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.repository.CalendarRepository;
import com.dungnt.healthclinic.repository.ClinicServiceRepository;
import com.dungnt.healthclinic.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarServiceImpl implements CalendarService {
    private CalendarRepository calendarRepository;
    private ClinicServiceRepository clinicServiceRepository;

    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository, ClinicServiceRepository clinicServiceRepository) {
        this.calendarRepository = calendarRepository;
        this.clinicServiceRepository  = clinicServiceRepository;
    }

    @Override
    public List<Calendar> findAll() {
        return (List<Calendar>) calendarRepository.findAll();
    }

    @Override
    public Optional<Calendar> findById(Long id) throws Exception {
        if (id == null) {
            throw new Exception("Gia tri id null");
        }
        return calendarRepository.findById(id);
    }

    @Override
    public void save(Calendar calendar) throws Exception {
//        if (calendar == null) {
//            throw new Exception("Doi tuong calendar null");
//        }
//        if (calendar.getDate() == null) {
//            throw new Exception("Gia tri date null");
//        }
//        if (calendar.getTimeStart() == null) {
//            throw new Exception("Gia tri thoi gian bat dau null");
//        }
//        if (calendar.getTimeEnd() == null) {
//            throw new Exception("Gia tri thoi gian ket thuc null");
//        }
//        if (calendar.getState() == null) {
//            throw new Exception("Gia tri state null");
//        }
//
//        boolean check = true;
//        List<Calendar> calendarList = calendarRepository.findAllByMedicalStaff(calendar.getMedicalStaff());
//        LocalTime timeStart = calendar.getTimeStart();
//        LocalTime timeEnd = calendar.getTimeEnd();
//
//        for (Calendar calendarTmp: calendarList) {
//            if (calendarTmp.getDate().isEqual(calendar.getDate())) {
//                LocalTime timeStartTmp = calendarTmp.getTimeStart();
//                LocalTime timeEndTmp = calendarTmp.getTimeEnd();
//                if (timeEnd.compareTo(timeStartTmp) >= 0 && timeEnd.compareTo(timeEndTmp) <= 0  ) {
//                    check = false;
//                    break;
//                }
//                if (timeEnd.compareTo(timeEndTmp) >= 0 && timeStart.compareTo(timeEndTmp) <= 0) {
//                    check = false;
//                    break;
//                }
//            }
//
//        }
//        if (!check) {
//            throw new Exception("Thoi gian ban chon khong phu hop do trung voi lich kham da co");
//        }

        calendarRepository.save(calendar);
    }

    @Override
    public void remove(Calendar calendar) {
        calendarRepository.delete(calendar);
    }

    /**
     *
     * @param calendarRequest
     * @param state
     * @return List
     * @throws Exception
     * @description phuong thuc tra ve lich hen theo tham so dau vao la ngay, gio, dich vu kham
     */

    @Override
    public List<Calendar> findSuitableCalendar(CalendarRequest calendarRequest, Integer state) throws Exception {
        if (calendarRequest == null) {
            throw new Exception("Gia tri calendar request null");
        }

        if (calendarRequest.getDate() == null) {
            throw new Exception("Gia tri date null");
        }
        if (calendarRequest.getTime() == null) {
            throw new Exception("Gia tri thoi gian bat dau null");
        }
        if (calendarRequest.getServiceId() == null) {
            throw new Exception("Gia tri serviceId null");
        }
        if (state == null) {
            throw new Exception("Gia tri state null");
        }
        LocalDate date = LocalDate.parse(calendarRequest.getDate());
        LocalTime timeStart = LocalTime.parse(calendarRequest.getTime());
        Long serviceId = Long.parseLong(calendarRequest.getServiceId());
        return calendarRepository.findSuitableCalendars(date, timeStart, serviceId, state);
    }

    /**
     *
     * @param calendarRequest
     * @param state
     * @return List
     * @throws Exception
     * @description phuong thuc goi y cac cuoc hen dua vao tham so dau vao la ngay, gio, dich vu kham
     */

    @Override
    public List<Calendar> recommendCalendars(CalendarRequest calendarRequest, Integer state) throws Exception {
        if (calendarRequest == null) {
            throw new Exception("Gia tri calendar request null");
        }

        if (calendarRequest.getDate() == null) {
            throw new Exception("Gia tri date null");
        }
        if (calendarRequest.getTime() == null) {
            throw new Exception("Gia tri thoi gian bat dau null");
        }
        if (calendarRequest.getServiceId() == null) {
            throw new Exception("Gia tri serviceId null");
        }
        if (state == null) {
            throw new Exception("Gia tri state null");
        }
        LocalDate date = LocalDate.parse(calendarRequest.getDate());
        LocalTime timeStart = LocalTime.parse(calendarRequest.getTime());
        Long serviceId = Long.parseLong(calendarRequest.getServiceId());
        List<Calendar> calendars = new ArrayList<>();
        calendars.addAll(calendarRepository.findRecommendedCalendars1(date, timeStart, serviceId, state));
        calendars.addAll(calendarRepository.findRecommendedCalendars2(date, serviceId, state));
        return calendars;
    }

    /**
     *
     * @param calendar
     * @throws Exception
     * @description Kiem tra trang thai cua lich
     */

    @Override
    public void checkCalendarState(Calendar calendar) throws Exception {
        if (calendar.getState() == 1) {
            throw new Exception("Lich kham da co nguoi dat");
        }
    }

    @Override
    public List<Calendar> findAllByDate(LocalDate date) throws Exception {
        if (date == null) {
            throw new Exception("Tham so date null");
        }
        return calendarRepository.findAllByDate(date);
    }

    @Override
    public List<Calendar> findAllByClinicService(ClinicService service) {
        return calendarRepository.findAllByClinicService(service);
    }

    @Override
    public List<Calendar> findAllByClinicServiceAndDate(ClinicService service, LocalDate date) {
        return calendarRepository.findAllByClinicServiceAndDate(service, date);
    }

    @Override
    public List<Calendar> findAllByMedicalStaff(User medicalStaff) {
        return calendarRepository.findAllByMedicalStaff(medicalStaff);
    }

    @Override
    public List<Calendar> findAllByDateAndMedicalStaff(LocalDate date, User medicalStaff) {
        return calendarRepository.findAllByDateAndMedicalStaff(date, medicalStaff);
    }


}
