package com.dungnt.healthclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @Column(name = "state")
    private Integer state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private ClinicService clinicService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_staff_id", referencedColumnName = "id")
    private User medicalStaff;

    @OneToOne(mappedBy = "calendar", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Appointment appointment;

    public Calendar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getClinicServiceId() {
        return clinicService.getId();
    }

    public String getClinicServiceName() {
        return clinicService.getName();
    }

    public String getClinicServiceDescription() {
        return clinicService.getDescription();
    }

    @JsonIgnore
    public ClinicService getClinicService() {
        return clinicService;
    }

    @JsonIgnore
    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @JsonIgnore
    public User getMedicalStaff() {
        return medicalStaff;
    }

    @JsonIgnore
    public void setMedicalStaff(User medicalStaff) {
        this.medicalStaff = medicalStaff;
    }

    public Long getMedicalStaffId() {
        return medicalStaff.getId();
    }

    public String getMedicalStaffName() {
        return medicalStaff.getName();
    }

    public String getMedicalStaffRoom() {
        return medicalStaff.getRoom();
    }
}
