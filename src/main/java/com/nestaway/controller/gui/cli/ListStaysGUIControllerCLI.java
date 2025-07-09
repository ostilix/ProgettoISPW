package com.nestaway.controller.gui.cli;

import com.nestaway.bean.StayBean;
import com.nestaway.controller.app.BookStayController;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.ListStaysView;

import java.time.LocalDate;
import java.util.List;

public class ListStaysGUIControllerCLI extends AbstractGUIControllerCLI{

    private final ListStaysView listStaysView = new ListStaysView();
    private List<StayBean> stays;
    private final String city;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Integer numGuests;

    ListStaysGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.city = SessionManager.getSessionManager().getSessionFromId(session).getCity();
        this.checkInDate = SessionManager.getSessionManager().getSessionFromId(session).getCheckIn();
        this.checkOutDate = SessionManager.getSessionManager().getSessionFromId(session).getCheckOut();
        this.numGuests = SessionManager.getSessionManager().getSessionFromId(session).getNumGuests();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        BookStayController bookStayController = new BookStayController();

        try {
            stays = bookStayController.findStays(city, checkInDate, checkOutDate, numGuests);

            int choice;
            choice = listStaysView.showMenu();

            switch (choice) {
                case 1 -> showStays();
                case 2 -> selectStay();
                case 3 -> goHome();
                case 4 -> exit();
                default -> throw new IllegalArgumentException("Invalid case!");
            }
        } catch (OperationFailedException e) {
            listStaysView.showError(e.getMessage());
        } catch (NotFoundException e) {
            listStaysView.showMessage(e.getMessage());
        }
    }

    private void showStays() {
        String[] staysFormatted = new String[this.stays.size()];
        int i = 0;
        for (StayBean stay : this.stays) {
            staysFormatted[i] = String.format(
                    "%d - Name: %s%n" +
                            "    City: %s%n" +
                            "    Price: %.2f€/night%n" +
                            "    Rooms: %d%n",
                    i + 1,
                    stay.getName(),
                    stay.getCity(),
                    stay.getPricePerNight(),
                    stay.getNumRooms()
            );
            i++;
        }

        listStaysView.showStays(staysFormatted);
        start();
    }

    private void selectStay() {
        int num = listStaysView.selectStay();
        if (num < 1 || num > stays.size()) {
            listStaysView.showMessage("Stay not found!");
            start();
        } else {
            StayBean stay = stays.get(num - 1);
            if (stay != null) {
                SessionManager.getSessionManager().getSessionFromId(currentSession).setStay(stay);

                StayDetailsGUIControllerCLI stayDetailsController = new StayDetailsGUIControllerCLI(currentSession, returningHome);
                stayDetailsController.start();
            }
            SessionManager.getSessionManager().getSessionFromId(currentSession).resetStay();
            if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
                start();
            }
        }
    }
}
