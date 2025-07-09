package com.nestaway.model;

import java.sql.Date;
import java.time.LocalDate;

public class Availability {
    private Integer idAvailability;
    private LocalDate date;
    private Boolean isAvailable;
    private Integer idStay;

    public Availability(Integer idAvailability, LocalDate date, Boolean isAvailable, Integer idStay) {
        this.idAvailability = idAvailability;
        this.date = date;
        this.isAvailable = isAvailable;
        this.idStay = idStay;
    }

    public Integer getIdAvailability() {return this.idAvailability;}

    public void setIdAvailability(Integer idAvailability) {
        this.idAvailability = idAvailability;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setAvailability(Boolean status){
        this.isAvailable = status;
    }

    public Integer getIdStay() {
        return this.idStay;
    }

    public void setIdStay(Integer idStay) {
        this.idStay = idStay;
    }
}

