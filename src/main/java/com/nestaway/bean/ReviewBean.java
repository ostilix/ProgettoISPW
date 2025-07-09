package com.nestaway.bean;

import java.time.LocalDate;

public class ReviewBean {
    private Integer idReview;
    private String bookingCode;
    private Integer rating;
    private String comment;
    private LocalDate date;
    private Integer idStay;

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
