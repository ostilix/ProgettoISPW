package com.nestaway.utils.dao;

import com.nestaway.exception.EncryptionException;
import com.nestaway.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemoDataLoader {
    public static void load() throws EncryptionException {
        MemoryDatabase.getHosts().clear();
        MemoryDatabase.getStays().clear();
        MemoryDatabase.getAvailabilities().clear();
        MemoryDatabase.getBookings().clear();
        MemoryDatabase.getReviews().clear();
        MemoryDatabase.getNotifications().clear();
        DemoIndex.bookingStayMap.clear();

        // Host
        Host host = new Host("Demo", "Demo", "Demo@gmail.com", "demo", "demo@gmail.com", "demo");
        MemoryDatabase.getHosts().add(host);

        // Stay
        Stay stay = new Stay(1, "Casa Roma", "Bellissima casa nel centro di Roma", "Rome",
                "Via Testaccio 12", 75.00, 4, 2, 1, host.getUsername());
        MemoryDatabase.getStays().add(stay);

        // Availability (opzionale se vuoi mantenere)
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            Availability availability = new Availability(i + 1, today.plusDays(i), true, stay.getIdStay());
            MemoryDatabase.getAvailabilities().add(availability);
        }

        // Booking
        Booking booking = new Booking("Luigi", "Verdi", "luigi.verdi@gmail.com", "3214567890",
                today.plusDays(1), today.plusDays(3), 2, true);
        booking.setIdAndCodeBooking("A2j0f030c5");
        MemoryDatabase.getBookings().add(booking);

        DemoIndex.bookingStayMap.put(booking.getCodeBooking(), stay.getIdStay());

        // Review
        Review review = new Review(booking.getCodeBooking(), 5, "Esperienza fantastica!",
                today.plusDays(4), stay.getIdStay());
        MemoryDatabase.getReviews().add(review);

        // Notification
        Notification notification = new Notification(TypeNotif.NEW, stay.getName(),
                booking.getCodeBooking(), LocalDateTime.of(today, java.time.LocalTime.now()));
        MemoryDatabase.getNotifications().add(notification);
    }
}
