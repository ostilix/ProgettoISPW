package com.nestaway.utils.dao;

import com.nestaway.exception.EncryptionException;
import com.nestaway.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemoDataLoader {
    public static void load() throws EncryptionException {
        MemoryDatabase.hosts.clear();
        MemoryDatabase.stays.clear();
        MemoryDatabase.availabilities.clear();
        MemoryDatabase.bookings.clear();
        MemoryDatabase.reviews.clear();
        MemoryDatabase.notifications.clear();
        DemoIndex.bookingStayMap.clear();

        // Host
        Host host = new Host("Demo", "Demo", "Demo@gmail.com", "demo", "demo@gmail.com", "demo");
        MemoryDatabase.hosts.add(host);

        // Stay
        Stay stay = new Stay(1, "Casa Roma", "Bellissima casa nel centro di Roma", "Rome",
                "Via Testaccio 12", 75.00, 4, 2, 1, host.getUsername());
        MemoryDatabase.stays.add(stay);

        // Availability (opzionale se vuoi mantenere)
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            Availability availability = new Availability(i + 1, today.plusDays(i), true, stay.getIdStay());
            MemoryDatabase.availabilities.add(availability);
        }

        // Booking
        Booking booking = new Booking("Luigi", "Verdi", "luigi.verdi@gmail.com", "3214567890",
                today.plusDays(1), today.plusDays(3), 2, true);
        booking.setIdAndCodeBooking("A2j0f030c5");
        MemoryDatabase.bookings.add(booking);

        DemoIndex.bookingStayMap.put(booking.getCodeBooking(), stay.getIdStay());

        // Review
        Review review = new Review(booking.getCodeBooking(), 5, "Esperienza fantastica!",
                today.plusDays(4), stay.getIdStay());
        MemoryDatabase.reviews.add(review);

        // Notification
        Notification notification = new Notification(TypeNotif.NEW, stay.getName(),
                booking.getCodeBooking(), LocalDateTime.of(today, java.time.LocalTime.now()));
        MemoryDatabase.notifications.add(notification);
    }
}
