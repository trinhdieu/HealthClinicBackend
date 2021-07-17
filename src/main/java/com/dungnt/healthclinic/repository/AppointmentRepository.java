package com.dungnt.healthclinic.repository;

import com.dungnt.healthclinic.model.Appointment;
import com.dungnt.healthclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "select appt.* from appointments appt where appt.client_id= :id", nativeQuery = true)
    List<Appointment> findAllByClientId(@Param("id") Long clientId);
    @Query(value = "select appt.* from appointments appt where appt.medical_staff_id= :id", nativeQuery = true)
    List<Appointment> findAllByMedicalStaffId(@Param("id") Long medicalStaffId);

    List<Appointment> findAllByClient(User client);
//    List<Appointment> findAllByMedicalStaff(User medicalStaff);
}
