package com.example.doctoragenda;
import org.joda.time.LocalTime;

import java.util.Calendar;

public class Appointment {
    private String doctor;
    private LocalTime hour;

    public Appointment(String doctor, Calendar calendar, LocalTime hour){
        this.doctor = doctor;
        this.hour = hour;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Fecha ocupada: Cita a las " + hour.toString();
    }
}
