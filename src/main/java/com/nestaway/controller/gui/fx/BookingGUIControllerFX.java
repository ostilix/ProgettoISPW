package com.nestaway.controller.gui.fx;

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
import com.nestaway.utils.view.fx.PageManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BookingGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Button back;

    @FXML
    Button confirm;

    @FXML
    TextField firstname;

    @FXML
    TextField lastname;

    @FXML
    TextField email;

    @FXML
    TextField telephone;

    @FXML
    RadioButton payPalRadio;

    @FXML
    RadioButton onSiteRadio;

    @FXML
    Label message;

    ToggleGroup paymentMethod;

    StayBean stay;

    @FXML
    public void goBack() {
        resetMsg(errorMsg, message);
        try {
            PageManagerSingleton.getInstance().goBack(currentSession);
        } catch (OperationFailedException | NotFoundException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    @FXML
    public void bookStay() {
        resetMsg(errorMsg, message);
        try {
            BookingBean booking = getBooking();
            BookStayController bookStayController = new BookStayController();

            List<AvailabilityBean> allAvailabilities = bookStayController.findAvailability(stay);
            List<AvailabilityBean> filteredAvailabilities = allAvailabilities.stream().filter(a -> !a.getDate().isBefore(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckIn()) && a.getDate().isBefore(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckOut())).toList();

            bookStayController.sendReservation(stay, booking, filteredAvailabilities);
            setMsg(message, "Booking successful! Your booking code is: " + booking.getCodeBooking());
        } catch (IncorrectDataException | DuplicateEntryException | DAOException | NotFoundException e) {
            setMsg(message, e.getMessage());
        } catch (OperationFailedException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    private BookingBean getBooking() throws IncorrectDataException {
        try {
            String[] data = {firstname.getText(), lastname.getText(), email.getText(), telephone.getText()};
            if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty()) {
                throw new IncorrectDataException("All fields are required!");
            } else if (paymentMethod.getSelectedToggle() == null) {
                throw new IncorrectDataException("Please select a payment method!");
            }

            BookingBean booking = new BookingBean();
            booking.setFirstName(data[0]);
            booking.setLastName(data[1]);
            booking.setEmailAddress(data[2]);
            booking.setTelephone(data[3]);
            booking.setCheckInDate(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckIn());
            booking.setCheckOutDate(SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckOut());
            booking.setNumGuests(SessionManager.getSessionManager().getSessionFromId(currentSession).getNumGuests());
            booking.setOnlinePayment(payPalRadio.isSelected());

            return booking;
        } catch (NumberFormatException e) {
            throw new IncorrectDataException("Invalid data");
        }
    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException {
        this.currentSession = session;
        resetMsg(errorMsg, message);

        stay = SessionManager.getSessionManager().getSessionFromId(session).getStay();
        paymentMethod = new ToggleGroup();
        payPalRadio.setToggleGroup(paymentMethod);
        onSiteRadio.setToggleGroup(paymentMethod);
    }
}

