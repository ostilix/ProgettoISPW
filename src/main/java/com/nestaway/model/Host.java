package com.nestaway.model;

import com.nestaway.exception.EncryptionException;

import java.util.ArrayList;
import java.util.List;

public class Host extends User {

    private final String firstName;
    private final String lastName;
    private String emailAddress;
    private final String infoPayPal;


    public Host(String firstName, String lastName, String emailAddress, String username, String infoPayPal, String password) throws EncryptionException {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.infoPayPal = infoPayPal;
    }

    private transient List<Stay> stays = new ArrayList<>();
    private transient List<Notification> notifications = new ArrayList<>();

    public void addStay(Stay stay) {
        this.stays.add(new Stay( stay.getIdStay(), stay.getName(), stay.getDescription(), stay.getCity(), stay.getAddress(), stay.getPricePerNight(), stay.getMaxGuests(), stay.getNumRooms(), stay.getNumBathrooms(), stay.getHostUsername()));
    }

    public List<Stay> getStays() {
        return this.stays;
    }

    public void modifyEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getInfoPayPal() {
        return this.infoPayPal;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(new Notification(notification.getType(), notification.getNameStay(), notification.getBookingCode(), notification.getDateAndTime()));
    }

    public void removeNotification(Notification notification) {
        this.notifications.removeIf(n -> n.getType().equals(notification.getType()) && n.getNameStay().equals(notification.getNameStay()) && n.getBookingCode().equals(notification.getBookingCode()) && n.getDateAndTime().equals(notification.getDateAndTime()));
    }

    public List<Notification> getNewNotification() {
        return this.notifications;
    }

    public void addNotification(List<Notification> notifications) {
        for (Notification notification : notifications) {
            this.addNotification(notification);
        }
    }

    public void removeNotif(List<Notification> notifications) {
        for (Notification notification : notifications) {
            this.removeNotification(notification);
        }
    }

    public void addStay(List<Stay> stays) {
        for (Stay stay : stays) {
            this.addStay(stay);
        }
    }

    public void setTransientParams() {
        this.stays = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }
}