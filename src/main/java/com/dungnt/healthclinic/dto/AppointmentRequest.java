package com.dungnt.healthclinic.dto;

public class AppointmentRequest {
    private Long calendarId;
    private Long clientId;

    public AppointmentRequest() {
    }

    public AppointmentRequest(Long calendarId, Long clientId) {
        this.calendarId = calendarId;
        this.clientId = clientId;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

}
