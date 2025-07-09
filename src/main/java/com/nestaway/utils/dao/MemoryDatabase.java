package com.nestaway.utils.dao;

import com.nestaway.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MemoryDatabase {

    private static final List<Stay> stays = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<Review> reviews = new ArrayList<>();
    private static final List<Availability> availabilities = new ArrayList<>();
    private static final List<Notification> notifications = new ArrayList<>();
    private static final List<Host> hosts = new ArrayList<>();

    // Costruttore privato per impedire istanze
    private MemoryDatabase() {
    }

    // --- Getter sicuri (con lista immutabile opzionale) ---
    public static List<Stay> getStays() {
        return stays;
    }

    public static List<Booking> getBookings() {
        return bookings;
    }

    public static List<Review> getReviews() {
        return reviews;
    }

    public static List<Availability> getAvailabilities() {
        return availabilities;
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static List<Host> getHosts() {
        return hosts;
    }
}