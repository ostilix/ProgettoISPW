package com.nestaway.utils;

import com.nestaway.bean.*;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.model.*;

import java.time.ZoneId;
import java.util.List;

public class ToBeanConverter {

    private ToBeanConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static StayBean fromStayToStayBean(Stay stay) throws IncorrectDataException {
        StayBean stayBean = new StayBean();
        stayBean.setIdStay(stay.getIdStay());
        stayBean.setName(stay.getName());
        stayBean.setDescription(stay.getDescription());
        stayBean.setCity(stay.getCity());
        stayBean.setAddress(stay.getAddress());
        stayBean.setPricePerNight(stay.getPricePerNight());
        stayBean.setMaxGuests(stay.getMaxGuests());
        stayBean.setNumRooms(stay.getNumRooms());
        stayBean.setNumBathrooms(stay.getNumBathrooms());
        stayBean.setHostUsername(stay.getHostUsername());

        List<Availability> availabilities = stay.getAvailability();

        if (availabilities != null) {
            for (Availability availability : availabilities) {
                stayBean.setAvailability(stayBean.getAvailability());
            }
        }

        if (stay.getReviews() != null) {
            List<ReviewBean> reviewBeans = stay.getReviews().stream()
                    .map(ToBeanConverter::fromReviewToReviewBean)
                            .toList();

            stayBean.setReviews(reviewBeans);
        }
        return stayBean;
    }

    public static NotificationBean fromNotificationToNotificationBean(Notification notification) {
        NotificationBean notificationBean = new NotificationBean();
        notificationBean.setType(notification.getType().toString());
        notificationBean.setNameStay(notification.getNameStay());
        notificationBean.setBookingCode(notification.getBookingCode());
        notificationBean.setDateAndTime(notification.getDateAndTime().atZone(ZoneId.systemDefault()));
        return notificationBean;
    }

    public static BookingBean fromBookingToBookingBean(Booking booking) throws IncorrectDataException {
        BookingBean bookingBean = new BookingBean();
        bookingBean.setCodeBooking(booking.getCodeBooking());
        bookingBean.setFirstName(booking.getFirstName());
        bookingBean.setLastName(booking.getLastName());
        bookingBean.setEmailAddress(booking.getEmailAddress());
        bookingBean.setTelephone(booking.getTelephone());
        bookingBean.setCheckInDate(booking.getCheckInDate());
        bookingBean.setCheckOutDate(booking.getCheckOutDate());
        bookingBean.setNumGuests(booking.getNumGuests());
        bookingBean.setOnlinePayment(booking.getOnlinePayment());
        return bookingBean;
    }

    public static HostBean fromHostToHostBean(Host host) throws IncorrectDataException {
        HostBean hostBean = new HostBean();
        hostBean.setUsername(host.getUsername());
        hostBean.setFirstName(host.getFirstName());
        hostBean.setLastName(host.getLastName());
        hostBean.setEmailAddress(host.getEmailAddress());
        hostBean.setInfoPayPal(host.getInfoPayPal());
        return hostBean;
    }

    public static AvailabilityBean fromAvailabilityToAvailabilityBean(Availability availability) throws IncorrectDataException {
        AvailabilityBean availabilityBean = new AvailabilityBean();
        availabilityBean.setIdAvailability(availability.getIdAvailability());
        availabilityBean.setDate(availability.getDate());
        availabilityBean.setIsAvailable(availability.getIsAvailable());
        availabilityBean.setIdStay(availability.getIdStay());
        return availabilityBean;
    }

    public static ReviewBean fromReviewToReviewBean(Review review) {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setIdReview(review.getIdReview());
        reviewBean.setBookingCode(review.getBookingCode());
        reviewBean.setRating(review.getRating());
        reviewBean.setComment(review.getComment());
        reviewBean.setDate(review.getDate());
        reviewBean.setIdStay(review.getIdStay());
        return reviewBean;
    }
}
