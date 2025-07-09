package com.nestaway.controller.app;

import com.nestaway.bean.AvailabilityBean;
import com.nestaway.bean.BookingBean;
import com.nestaway.bean.StayBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookStayControllerTest {

    private static BookStayController controller;

    @BeforeAll
    static void setup() {
        System.setProperty("DAO_TYPE", "JDBC");
        controller = new BookStayController();
    }

    @Test
    void testFindStays() {
        assertDoesNotThrow(() -> {
            List<StayBean> stays = controller.findStays("Rome", LocalDate.of(2025, 8, 5), LocalDate.of(2025, 8, 6), 2);
            assertFalse(stays.isEmpty(), "Should return some stays for Rome");
        });
    }

    @Test
    void testFindAvailability() throws Exception {
        List<StayBean> stays = controller.findStays("Milan", LocalDate.of(2025, 8, 5), LocalDate.of(2025, 8, 6), 2);
        StayBean stay = stays.get(0);
        List<AvailabilityBean> av = controller.findAvailability(stay);
        assertNotNull(av);
        assertFalse(av.isEmpty(), "Availability should not be empty");
    }

    @Test
    void testSendReservation() throws Exception {
        List<StayBean> stays = controller.findStays("Rome", LocalDate.of(2025, 8, 5), LocalDate.of(2025, 8, 6), 2);
        StayBean stay = stays.get(0);
        List<AvailabilityBean> av = controller.findAvailability(stay);

        BookingBean booking = new BookingBean();
        booking.setFirstName("Test");
        booking.setLastName("User");
        booking.setEmailAddress("test.user@example.com");
        booking.setTelephone("+391234567890");
        booking.setCheckInDate(LocalDate.of(2025, 8, 5));
        booking.setCheckOutDate(LocalDate.of(2025, 8, 6));
        booking.setNumGuests(2);
        booking.setOnlinePayment(false);

        controller.sendReservation(stay, booking, av);
        assertNotNull(booking.getCodeBooking(), "Booking code must be set after reservation");
    }
}
