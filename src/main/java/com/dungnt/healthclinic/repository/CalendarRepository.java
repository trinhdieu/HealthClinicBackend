package com.dungnt.healthclinic.repository;

import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @Query(value = "Select ca.* from calendars ca where ca.date = :date and ca.time_start = :timeStart and ca.service_id = :id and ca.state = :state", nativeQuery = true)
    List<Calendar> findSuitableCalendars(@Param("date")LocalDate date, @Param("timeStart")LocalTime timeStart, @Param("id")Long serviceId, @Param("state") Integer state);

    @Query(value = "Select ca.* from calendars ca where ca.date >= :date and ca.time_start = :timeStart and ca.service_id = :id and ca.state = :state", nativeQuery = true)
    List<Calendar> findRecommendedCalendars1(@Param("date")LocalDate date, @Param("timeStart")LocalTime timeStart, @Param("id")Long serviceId, @Param("state") Integer state);

    @Query(value = "select ca.* from calendars ca where ca.date = :date and ca.service_id = :id and ca.state = :state", nativeQuery = true)
    List<Calendar> findRecommendedCalendars2(@Param("date")LocalDate date, @Param("id")Long serviceId, @Param("state") Integer state);

//    List<Calendar> findAllByDateAndRoom(LocalDate date, String room);
//    List<Calendar> findAllByRoom(String room);
    List<Calendar> findAllByMedicalStaff(User medicalStaff);
    List<Calendar> findAllByDate(LocalDate date);
    List<Calendar> findAllByClinicService(ClinicService service);
    List<Calendar> findAllByClinicServiceAndDate(ClinicService service, LocalDate date);
    List<Calendar> findAllByDateAndMedicalStaff(LocalDate date, User medicalStaff);
}
