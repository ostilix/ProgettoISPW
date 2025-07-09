package com.nestaway.utils.dao;

import java.util.HashMap;
import java.util.Map;

public class DemoIndex {

    private static final Map<String, Integer> bookingStayMap = new HashMap<>();

    private DemoIndex() {
    }

    public static Map<String, Integer> getBookingStayMap() {
        return bookingStayMap;
    }
}