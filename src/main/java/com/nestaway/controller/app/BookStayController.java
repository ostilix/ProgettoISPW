package com.nestaway.controller.app;

import com.nestaway.bean.AvailabilityBean;
import com.nestaway.bean.BookingBean;
import com.nestaway.bean.StayBean;
import com.nestaway.dao.AvailabilityDAO;
import com.nestaway.dao.BookingDAO;
import com.nestaway.dao.HostDAO;
import com.nestaway.dao.StayDAO;
import com.nestaway.exception.DuplicateEntryException;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.*;
import com.nestaway.utils.ToBeanConverter;
import com.nestaway.utils.dao.factory.FactorySingletonDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nestaway.exception.dao.TypeDAOException.DUPLICATE;
import static com.nestaway.exception.dao.TypeDAOException.NOT_AVAILABLE;

public class BookStayController {

    private final BookingDAO bookingDAO;
    private final StayDAO stayDAO;
    private final HostDAO hostDAO;
    private final AvailabilityDAO availabilityDAO;

    public BookStayController() {
        this.bookingDAO = FactorySingletonDAO.getDefaultDAO().getBookingDAO();
        this.stayDAO = FactorySingletonDAO.getDefaultDAO().getStayDAO();
        this.hostDAO = FactorySingletonDAO.getDefaultDAO().getHostDAO();
        this.availabilityDAO = FactorySingletonDAO.getDefaultDAO().getAvailabilityDAO();
    }

    public List<StayBean> findStays(String city, LocalDate from, LocalDate to, Integer numGuests) throws OperationFailedException, NotFoundException {
        try {
            List<StayBean> result = new ArrayList<>();
            List<Stay> stays = stayDAO.selectStayByCity(city);

            for (Stay stay : stays) {
                if (stay.getMaxGuests() < numGuests) {
                    continue;
                }
                List<Availability> availabilityList = availabilityDAO.selectInRange(stay.getIdStay(), from, to);
                stay.setAvailability(availabilityList);

                if (stay.isAvailableInRange(from, to)) {
                    result.add(ToBeanConverter.fromStayToStayBean(stay));
                }
            }

            if (result.isEmpty()) {
                throw new NotFoundException("No available stays found in " + city + " for the given period.");
            }

            return result;

        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException("DAO error in findStays");
        } catch (IncorrectDataException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            throw new OperationFailedException("Invalid data in findStays");
        }
    }

    public List<AvailabilityBean> findAvailability(StayBean stayBean) throws OperationFailedException, NotFoundException {
        try {
            Stay stay = stayDAO.selectStay(stayBean.getIdStay());
            if (stay == null) {
                throw new NotFoundException("Stay not found.");
            }

            List<Availability> availabilities = availabilityDAO.selectByStay(stayBean.getIdStay());
            if (availabilities.isEmpty()) {
                String msg = "No availability found for stay: " + stayBean.getIdStay();
                Logger.getGlobal().log(Level.SEVERE, msg);
                throw new OperationFailedException("No availability in findAvailability");
            }

            List<AvailabilityBean> availabilityBeans = new ArrayList<>();
            for (Availability a : availabilities) {
                availabilityBeans.add(ToBeanConverter.fromAvailabilityToAvailabilityBean(a));
            }
            return availabilityBeans;

        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException("DAO error in findAvailability");
        } catch (IncorrectDataException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            throw new OperationFailedException("Invalid data in findAvailability");
        }
    }

    public void sendReservation(StayBean stayBean, BookingBean bookingBean, List<AvailabilityBean> availabilityBean)
            throws OperationFailedException, DAOException, DuplicateEntryException {
        try {
            checkBookingValid(bookingBean, availabilityBean);

            Host host = hostDAO.selectHost(stayBean.getHostUsername());
            if (host == null) {
                String msg = "Inconsistent data. Host not found for stay: " + stayBean.getIdStay();
                Logger.getGlobal().log(Level.SEVERE, msg);
                throw new OperationFailedException("Host not found in sendReservation");
            }

            Booking booking = new Booking(bookingBean.getFirstName(), bookingBean.getLastName(), bookingBean.getEmailAddress(),
                    bookingBean.getTelephone(), bookingBean.getCheckInDate(), bookingBean.getCheckOutDate(),
                    bookingBean.getNumGuests(), bookingBean.getOnlinePayment());

            if (bookingBean.getOnlinePayment().equals(true)) {
                Double pricePerNight = stayBean.getPricePerNight();
                long numNights = ChronoUnit.DAYS.between(bookingBean.getCheckInDate(), bookingBean.getCheckOutDate());
                Double amount = pricePerNight * numNights;
                OnlinePaymentController onlinePaymentController = new OnlinePaymentController();
                boolean response = onlinePaymentController.payPayPal(host, amount, "Booking for stay: " + stayBean.getName());
                if (!response) {
                    throw new OperationFailedException("Payment failed in sendReservation");
                }
            }

            booking = bookingDAO.addBooking(stayBean.getIdStay(), booking);

            NotificationsController notificationsController = new NotificationsController();
            notificationsController.notifyHost(new Notification(TypeNotif.NEW, stayBean.getName(), booking.getCodeBooking(), LocalDateTime.now()), host);

            updateAvailabilityRemovingBookedDates(stayBean, bookingBean.getCheckInDate(), bookingBean.getCheckOutDate());

            bookingBean.setCodeBooking(booking.getCodeBooking());

        } catch (DAOException e) {
            if (e.getTypeException().equals(DUPLICATE)) {
                throw new DuplicateEntryException(e.getMessage() + ", if you have already paid, contact support.");
            } else if (e.getTypeException().equals(NOT_AVAILABLE)) {
                throw new OperationFailedException("No availabilities for this stay. If you have already paid, contact support.");
            } else {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
                throw new OperationFailedException("General DAO error in sendReservation");
            }
        }
    }

    public void updateAvailabilityRemovingBookedDates(StayBean stayBean, LocalDate checkInDate, LocalDate checkOutDate)
            throws OperationFailedException {
        try {
            availabilityDAO.deleteInRange(checkInDate, checkOutDate, stayBean.getIdStay());
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, "BookStayController.updateAvailabilityRemovingBookedDates - DAO error", e);
            throw new OperationFailedException("DAO error in updateAvailabilityRemovingBookedDates");
        }
    }


    private void checkBookingValid(BookingBean bookingBean, List<AvailabilityBean> availabilityBeans) throws OperationFailedException {
        LocalDate checkIn = bookingBean.getCheckInDate();
        LocalDate checkOut = bookingBean.getCheckOutDate();

        if (checkIn == null || checkOut == null || !checkIn.isBefore(checkOut)) {
            throw new OperationFailedException("Invalid check-in/check-out dates.");
        }

        if (bookingBean.getNumGuests() == null || bookingBean.getNumGuests() <= 0) {
            throw new OperationFailedException("Invalid number of guests.");
        }

        List<LocalDate> requestedDates = checkIn.datesUntil(checkOut).toList();

        for (LocalDate date : requestedDates) {
            boolean foundAvailable = availabilityBeans.stream()
                    .anyMatch(a -> date.equals(a.getDate()) && Boolean.TRUE.equals(a.getIsAvailable()));
            if (!foundAvailable) {
                throw new OperationFailedException("Date not available for booking: " + date);
            }
        }
    }
}
