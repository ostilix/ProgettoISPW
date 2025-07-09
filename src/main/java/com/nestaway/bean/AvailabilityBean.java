package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;

import java.time.LocalDate;

public class AvailabilityBean {
    private Integer idAvailability;
    private LocalDate date;
    private Boolean isAvailable;
    private Integer idStay;

    public Integer getIdAvailability() {
        return idAvailability;
    }

    public void setIdAvailability(Integer idAvailability) {
        this.idAvailability = idAvailability;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) throws IncorrectDataException {
        if (date == null) {
            throw new IncorrectDataException("Date cannot be null.");
        }
        this.date = date;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public Integer getIdStay() {
        return idStay;
    }

    public void setIdStay(Integer idStay) {
        this.idStay = idStay;
    }


}
