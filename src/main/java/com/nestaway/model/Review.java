package com.nestaway.model;

import java.time.LocalDate;

public class Review {

    private Integer idReview = null;
    private String bookingCode;
    private Integer rating;
    private String comment;
    private LocalDate date;
    private Integer idStay;

    public Review(String bookingCode, Integer rating, String comment, LocalDate date, Integer idStay) {
        this.bookingCode = bookingCode;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.idStay = idStay;
    }

    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }

    public Integer getIdReview() {
        return this.idReview;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Integer getIdStay() {
        return this.idStay;
    }

    public void setIdStay(Integer idStay) {
        this.idStay = idStay;
    }


}

