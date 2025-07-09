package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;

import java.util.regex.Pattern;

public class HostBean extends UserBean {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String infoPayPal;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IncorrectDataException {
        String firstNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(firstNamePattern, firstName);
        if (!match) {
            throw new IncorrectDataException("Firstname is not valid.");
        } else if (firstName.length() > 45){
            throw new IncorrectDataException("Firstname too long (max 45 characters).");
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IncorrectDataException {
        String lastNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?('[A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(lastNamePattern, lastName);
        if (!match) {
            throw new IncorrectDataException("Lastname is not valid.");
        } else if (lastName.length() > 45){
            throw new IncorrectDataException("Lastname too long (max 45 characters).");
        } else {
            this.lastName = lastName;
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) throws IncorrectDataException {
        String emailPattern = "^[\\w-.]++@([\\w-]++\\.)++[\\w-]{2,3}$";
        boolean match = Pattern.matches(emailPattern, emailAddress);
        if (!match) {
            throw new IncorrectDataException("Email is not valid.");
        } else if (emailAddress.length() > 45){
            throw new IncorrectDataException("Email too long (max 45 characters).");
        } else {
            this.emailAddress = emailAddress;
        }
    }

    public String getInfoPayPal() {
        return infoPayPal;
    }

    public void setInfoPayPal(String infoPayPal) throws IncorrectDataException {
        String infoPayPalPattern = "^[\\w-.]++@([\\w-]++\\.)++[\\w-]{2,3}$";
        boolean match = Pattern.matches(infoPayPalPattern, infoPayPal);
        if (!match) {
            throw new IncorrectDataException("InfoPayPal is not valid.");
        } else if (infoPayPal.length() > 45){
            throw new IncorrectDataException("InfoPayPal too long (max 45 characters).");
        } else {
            this.infoPayPal = infoPayPal;
        }
    }
}
