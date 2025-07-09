package com.nestaway.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Stay {

    private String name;
    private Integer idStay;
    private String description;
    private String city;
    private String address;
    private Double pricePerNight;
    private Integer maxGuests;
    private Integer numRooms;
    private Integer numBathrooms;
    private String hostUsername;

    private transient List<Review> reviews;
    private transient List<Availability> availability;

    public Stay(Integer idStay, String name, String description, String city, String address, Double pricePerNight, Integer maxGuests, Integer numRooms, Integer numBathrooms, String hostUsername) {
        this.idStay = idStay;
        this.name = name;
        this.description = description;
        this.city = city;
        this.address = address;
        this.pricePerNight = pricePerNight;
        this.maxGuests = maxGuests;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.hostUsername = hostUsername;
    }

    public void setIdStay(int idStay) {
        this.idStay = idStay;
    }

    public Integer getIdStay() {
        return this.idStay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Double getPricePerNight() {
        return this.pricePerNight;
    }

    public void setMaxGuests(Integer maxGuests) {
        this.maxGuests = maxGuests;
    }

    public Integer getMaxGuests() {
        return this.maxGuests;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Integer getNumRooms() {
        return this.numRooms;
    }

    public void setNumBathrooms(Integer numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public Integer getNumBathrooms() {
        return this.numBathrooms;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public String getHostUsername() {
        return this.hostUsername;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    public void addAvailability(Availability a) {
        if (this.availability == null) {
            this.availability = new ArrayList<>();
        }
        this.availability.add(a);
    }

    public void addAvailabilities(List<Availability> availabilities) {
        if (this.availability == null) {
            this.availability = new ArrayList<>();
        }
        this.availability.addAll(availabilities);
    }

    public List<Availability> getAvailability() {
        return this.availability;
    }

    public boolean isAvailableInRange(LocalDate start, LocalDate end) {
        if (availability == null) return false;

        Set<LocalDate> availableDates = availability.stream()
                .filter(Availability::getIsAvailable)
                .map(Availability::getDate)
                .collect(Collectors.toSet());

        List<LocalDate> requestedDates = start.datesUntil(end.plusDays(1)).toList();

        return availableDates.containsAll(requestedDates);
    }

    public void setReview(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }
        this.reviews.add(review);
    }

    public void addReviews(List<Review> reviews) {
        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }
        this.reviews.addAll(reviews);
    }


    public void setTransientParams() {
        this.reviews = new ArrayList<>();
        this.availability = new ArrayList<>();
    }
}
