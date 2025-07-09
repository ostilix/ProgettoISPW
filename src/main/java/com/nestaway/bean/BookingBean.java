package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class BookingBean {
    private Integer idStay;
    private String codeBooking;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String telephone;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer numGuests;
    private Boolean onlinePayment;

    public String getCodeBooking() {
        return this.codeBooking;
    }

    public void setCodeBooking(String code){
        this.codeBooking = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IncorrectDataException {
        String pattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?$";
        if (firstName == null || !Pattern.matches(pattern, firstName)) {
            throw new IncorrectDataException("First name is not valid.");
        }
        if (firstName.length() > 45) {
            throw new IncorrectDataException("First name is too long (max 45 characters).");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IncorrectDataException {
        String pattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?('[A-Z][a-zA-Z]*)?$";
        if (lastName == null || !Pattern.matches(pattern, lastName)) {
            throw new IncorrectDataException("Last name is not valid.");
        }
        if (lastName.length() > 45) {
            throw new IncorrectDataException("Last name is too long (max 45 characters).");
        }
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) throws IncorrectDataException {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";
        if (emailAddress == null || emailAddress.isBlank() || !Pattern.matches(emailPattern, emailAddress)) {
            throw new IncorrectDataException("Email address is not valid.");
        }
        if (emailAddress.length() > 45) {
            throw new IncorrectDataException("Email address is too long (max 45 characters).");
        }
        this.emailAddress = emailAddress;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws IncorrectDataException {
        String phonePattern = "^\\+?\\{6,15}$";
        if (telephone == null || !Pattern.matches(phonePattern, telephone)) {
            throw new IncorrectDataException("Telephone number is not valid.");
        }
        this.telephone = telephone;
    }

    public LocalDate getCheckInDate() {
        return checkInDate.toLocalDate();
    }

    public void setCheckInDate(LocalDate checkInDate) throws IncorrectDataException {
        if (checkInDate == null) {
            throw new IncorrectDataException("Check-in date cannot be null.");
        }
        this.checkInDate = Date.valueOf(checkInDate);
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate.toLocalDate();
    }

    public void setCheckOutDate(LocalDate checkOutDate) throws IncorrectDataException {
        if (checkOutDate == null) {
            throw new IncorrectDataException("Check-out date cannot be null.");
        }
        if (checkInDate != null && checkOutDate.isBefore(checkInDate.toLocalDate())) {
            throw new IncorrectDataException("Check-out date cannot be before check-in date.");
        }
        this.checkOutDate = Date.valueOf(checkOutDate);
    }

    public Integer getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(Integer numGuests) throws IncorrectDataException {
        if (numGuests == null || numGuests <= 0) {
            throw new IncorrectDataException("Number of guests must be greater than zero.");
        }
        this.numGuests = numGuests;
    }

    public Boolean getOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(Boolean onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public void setIdStay(Integer idStay) {
        this.idStay = idStay;
    }

    public Integer getIdStay() {
        return idStay;
    }
}

