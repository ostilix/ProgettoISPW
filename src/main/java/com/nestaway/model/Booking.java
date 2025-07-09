package com.nestaway.model;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

public class Booking {
    private Integer idBooking = null;
    private String codeBooking = null;
    private final String firstName;
    private final String lastName;
    private String emailAddress;
    private String telephone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numGuests;
    private Boolean onlinePayment;

    public Booking(String firstName, String lastName, String emailAddress, String telephone, LocalDate checkInDate, LocalDate checkOutDate, Integer numGuests, Boolean onlinePayment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.telephone = telephone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numGuests = numGuests;
        this.onlinePayment = onlinePayment;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setIdAndCodeBooking(Integer idBooking) {
        this.idBooking = idBooking;
        this.codeBooking = generateCodeBooking(idBooking);
    }

    public void setIdAndCodeBooking(String codeBooking) {
        this.codeBooking = codeBooking;
        this.idBooking = reverseCodeBooking(codeBooking);
    }

    public Integer getIdBooking(){
        return this.idBooking;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public LocalDate getCheckInDate() {
        return this.checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return this.checkOutDate;
    }

    public Integer getNumGuests() {
        return this.numGuests;
    }

    public Boolean getOnlinePayment() {
        return this.onlinePayment;
    }

    public String getCodeBooking() {
        return this.codeBooking;
    }

    private String generateCodeBooking(Integer idBooking) {
        String numStr = String.format("%04d", idBooking);

        char[] digits = new char[4];
        digits[0] = numStr.charAt(0);
        digits[1] = numStr.charAt(1);
        digits[2] = numStr.charAt(2);
        digits[3] = numStr.charAt(3);

        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";

        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }

        String randomString = sb.toString();

        char[] resultArray = randomString.toCharArray();

        resultArray[1] = digits[3];
        resultArray[3] = digits[0];
        resultArray[5] = digits[2];
        resultArray[7] = digits[1];

        return new String(resultArray);
    }

    private Integer reverseCodeBooking(String codeBooking) {
        char[] resultArray = codeBooking.toCharArray();
        char[] digits = new char[4];

        digits[0] = resultArray[3];
        digits[1] = resultArray[1];
        digits[2] = resultArray[7];
        digits[3] = resultArray[5];

        String numStr = String.valueOf(digits);

        return Integer.parseInt(numStr);
    }
}