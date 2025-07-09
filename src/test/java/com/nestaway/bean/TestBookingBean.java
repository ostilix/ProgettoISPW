package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TestBookingBean {
    @Test
    void testSetEmail() throws IncorrectDataException {
        BookingBean bookingBean = new BookingBean();
        bookingBean.setEmailAddress("2p.ro.v_a0@tor.vergata.com");
        Assertions.assertEquals("2p.ro.v_a0@tor.vergata.com", bookingBean.getEmailAddress());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "pr@va@torvergata.com", // DoubleAt
            "provatorvergata.com",  // NoAt
            "prova@torvergatacom", // NoDot
            "prova@torvergata.", // NoDomain
            "@torvergata.com", // NoUser
            "prova@torvergata", // NoTLD
            "prova@torvergata.comm", // LongTLD
            "prova@torvergata.c" // ShortTLD
    })
    void testSetEmailIncorrect(String email) {
        String errorMess = "";
        BookingBean bookingBean = new BookingBean();
        try {
            bookingBean.setEmailAddress(email);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Email address is not valid.", errorMess);
    }

    @Test
    void testSetTelephone() throws IncorrectDataException {
        BookingBean bookingBean = new BookingBean();
        bookingBean.setTelephone("+393895996644");
        Assertions.assertEquals("+393895996644", bookingBean.getTelephone());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+39 340 1234567", //  Contains spaces
            "+1234567890123456 ", // 16 digits, above the maximum of 15
            "+3934012abc567 ", //  Only 5 digits, below the minimum of 6
            "12345", // NoPlus
    })
    void testSetTelephoneIncorrect(String telephone) {
        String errorMess = "";
        BookingBean bookingBean = new BookingBean();
        try {
            bookingBean.setTelephone(telephone);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Telephone number is not valid.", errorMess);
    }

    @Test
    void testSetFirstName() throws IncorrectDataException {
        BookingBean bookingBean = new BookingBean();
        bookingBean.setFirstName("Luca Flavio");
        Assertions.assertEquals("Luca Flavio", bookingBean.getFirstName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "luca", // Lowercase
            "Luca1", // WithNumber
            "Luca@", // WithSpecialChar
            "Luca ", // WithSpace
    })
    void testSetFirstNameIncorrect(String firstName) {
        String errorMess = "";
        BookingBean bookingBean = new BookingBean();
        try {
            bookingBean.setFirstName(firstName);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("First name is not valid.", errorMess);
    }

    @Test
    void testSetLastName() throws IncorrectDataException {
        BookingBean bookingBean = new BookingBean();
        bookingBean.setLastName("O'Brien");
        Assertions.assertEquals("O'Brien", bookingBean.getLastName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "martorelli", // Lowercase
            "Martorelli1", // WithNumber
            "Martorelli@", // WithSpecialChar
            "Martorelli ", // WithSpace
    })
    void testSetLastNameIncorrect(String lastName) {
        String errorMess = "";
        BookingBean bookingBean = new BookingBean();
        try {
            bookingBean.setLastName(lastName);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Last name is not valid.", errorMess);
    }
}
