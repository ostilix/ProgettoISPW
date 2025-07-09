package com.nestaway.model;

import java.time.LocalDateTime;

public class Notification {

    private final TypeNotif type;
    private final String nameStay;
    private final String bookingCode;
    private final LocalDateTime dateAndTime;

    public Notification(TypeNotif type, String nameStay, String bookingCode, LocalDateTime dateAndTime) {
        this.type = type;
        this.nameStay = nameStay;
        this.bookingCode = bookingCode;
        this.dateAndTime = dateAndTime;
    }

    public TypeNotif getType() {
        return this.type;
    }

    public String getNameStay() {
        return this.nameStay;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public LocalDateTime getDateAndTime() {
        return this.dateAndTime;
    }
}
