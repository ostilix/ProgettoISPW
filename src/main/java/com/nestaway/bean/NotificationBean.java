package com.nestaway.bean;

import java.time.ZonedDateTime;

public class NotificationBean {

    private String type;
    private String nameStay;
    private String bookingCode;
    private ZonedDateTime dateAndTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameStay() {
        return this.nameStay;
    }

    public void setNameStay(String nameStay) {
        this.nameStay = nameStay;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public ZonedDateTime getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(ZonedDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

}
