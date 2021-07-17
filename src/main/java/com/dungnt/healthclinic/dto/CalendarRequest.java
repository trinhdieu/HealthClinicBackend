package com.dungnt.healthclinic.dto;


public class CalendarRequest {
    private String date;
    private String time;
    private String serviceId;

    public CalendarRequest() {
    }

    public CalendarRequest(String date, String time, String serviceId) {
        this.date = date;
        this.time = time;
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
