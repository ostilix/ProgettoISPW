package com.nestaway.controller.gui.cli;

import com.nestaway.bean.AvailabilityBean;
import com.nestaway.bean.BookingBean;
import com.nestaway.bean.StayBean;
import com.nestaway.controller.app.BookStayController;
import com.nestaway.exception.DuplicateEntryException;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.BookingView;

import java.time.LocalDate;
import java.util.List;

public class BookingGUIControllerCLI extends AbstractGUIControllerCLI {
    private final BookingView bookingView = new BookingView();
    private final StayBean stay;
    private List<AvailabilityBean> availabilities;

    public BookingGUIControllerCLI(Integer session, ReturningHome returningHome) {
        this.currentSession = session;
        this.stay = SessionManager.getSessionManager().getSessionFromId(session).getStay();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        BookStayController controller = new BookStayController();

        try {
            this.availabilities = controller.findAvailability(stay);

            int choice;
            choice = bookingView.showMenu();

            switch (choice) {
                case 1 -> showAvailabilities();
                case 2 -> bookStay();
                case 3 -> goBack();
                case 4 -> goHome();
                case 5 -> exit();
                default -> throw new IllegalArgumentException("Invalid case!");
            }
        } catch (OperationFailedException e) {
            bookingView.showError(e.getMessage());
        } catch (NotFoundException e) {
            bookingView.showMessage(e.getMessage());
        }
    }

    private void showAvailabilities() {
        LocalDate today = LocalDate.now();
        List<AvailabilityBean> futureAvailabilities = availabilities.stream().filter(a -> !a.getDate().isBefore(today)).toList();

        String[] as = new String[futureAvailabilities.size()];
        int i = 0;
        for (AvailabilityBean a : futureAvailabilities) {
            String status = a.getIsAvailable() ? "Available" : "Unavailable";
            as[i] = String.format("%d - Date: %s → %s%n", i + 1, a.getDate().toString(), status);
            i++;
        }

        bookingView.showAvailability(as);
        start();
    }

    public void bookStay() {
        try {
            String[] data = bookingView.insertBookingData();
            BookingBean booking = new BookingBean();

            if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty() || data[4].isEmpty()) {
                throw new IncorrectDataException("All fields are required!");
            }

            booking.setFirstName(data[0]);
            booking.setLastName(data[1]);
            booking.setEmailAddress(data[2]);
            booking.setTelephone(data[3]);
            booking.setCheckInDate(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckIn());
            booking.setCheckOutDate(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckOut());
            booking.setNumGuests(SessionManager.getSessionManager().getSessionFromId(currentSession).getNumGuests());
            booking.setOnlinePayment(Boolean.valueOf(data[4]));

            BookStayController controller = new BookStayController();
            this.availabilities = controller.findAvailability(stay);
            List<AvailabilityBean> filtered = availabilities.stream()
                    .filter(a -> !a.getDate().isBefore(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckIn()) && a.getDate().isBefore(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckOut())).toList();

            controller.sendReservation(stay, booking, filtered);
            bookingView.showMessage("Booking successful! Your booking code is: " + booking.getCodeBooking());
        } catch (OperationFailedException | DuplicateEntryException e) {
            bookingView.showError(e.getMessage());
        } catch (IncorrectDataException | NotFoundException | DAOException e) {
            bookingView.showMessage(e.getMessage());
        }
        start();
    }
}

