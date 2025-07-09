package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;

import java.util.List;

public class StayBean {

    private Integer idStay;
    private String name;
    private String description;
    private String city;
    private String address;
    private Double pricePerNight;
    private Integer maxGuests;
    private Integer numRooms;
    private Integer numBathrooms;
    private String hostUsername;

    private List<AvailabilityBean> availability;
    private List<ReviewBean> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) throws IncorrectDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new IncorrectDataException("Name cannot be empty.");
        }
        this.name = name;
    }

    public Integer getIdStay() {
        return idStay;
    }

    public void setIdStay(Integer idStay) throws IncorrectDataException {
        if (idStay == null || idStay < 0) {
            throw new IncorrectDataException("ID must be a non-negative number.");
        }
        this.idStay = idStay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IncorrectDataException {
        if (description == null || description.trim().isEmpty()) {
            throw new IncorrectDataException("Description cannot be empty.");
        }
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws IncorrectDataException {
        if (city == null || city.trim().isEmpty()) {
            throw new IncorrectDataException("City cannot be empty.");
        }
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IncorrectDataException {
        if (address == null || address.trim().isEmpty()) {
            throw new IncorrectDataException("Address cannot be empty.");
        }
        this.address = address;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) throws IncorrectDataException {
        if (pricePerNight == null || pricePerNight <= 0) {
            throw new IncorrectDataException("Price per night must be positive.");
        }
        this.pricePerNight = pricePerNight;
    }

    public Integer getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(Integer maxGuests) throws IncorrectDataException {
        if (maxGuests == null || maxGuests <= 0) {
            throw new IncorrectDataException("Max guests must be greater than zero.");
        }
        this.maxGuests = maxGuests;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) throws IncorrectDataException {
        if (numRooms == null || numRooms <= 0) {
            throw new IncorrectDataException("Number of rooms must be greater than zero.");
        }
        this.numRooms = numRooms;
    }

    public Integer getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(Integer numBathrooms) throws IncorrectDataException {
        if (numBathrooms == null || numBathrooms < 0) {
            throw new IncorrectDataException("Number of bathrooms cannot be negative.");
        }
        this.numBathrooms = numBathrooms;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) throws IncorrectDataException {
        if (hostUsername == null || hostUsername.trim().isEmpty()) {
            throw new IncorrectDataException("Host username cannot be empty.");
        }
        this.hostUsername = hostUsername;
    }

    public List<AvailabilityBean> getAvailability() {
        return availability;
    }

    public void setAvailability(List<AvailabilityBean> availability) {
        this.availability = availability;
    }

    public List<ReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewBean> reviews) {
        this.reviews = reviews;
    }

}
