package com.nestaway.utils.dao;

import com.nestaway.model.*;

import java.util.ArrayList;
import java.util.List;

public class MemoryDatabase {
    public static final List<Stay> stays = new ArrayList<>();
    public static final List<Booking> bookings = new ArrayList<>();
    public static final List<Review> reviews = new ArrayList<>();
    public static final List<Availability> availabilities = new ArrayList<>();
    public static final List<Notification> notifications = new ArrayList<>();
    public static final List<Host> hosts = new ArrayList<>();
}

