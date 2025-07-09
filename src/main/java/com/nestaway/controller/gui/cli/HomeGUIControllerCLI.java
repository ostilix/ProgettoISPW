package com.nestaway.controller.gui.cli;

import com.nestaway.utils.Session;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.HomeView;

import java.time.LocalDate;

public class HomeGUIControllerCLI extends AbstractGUIControllerCLI {

    private final HomeView view = new HomeView();

    public HomeGUIControllerCLI(Integer session, ReturningHome returningHome) {
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start() {
        int choice;
        choice = view.showMenu();

        switch (choice) {
            case 1 -> searchStays();
            case 2 -> loginPage();
            case 3 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void loginPage() {
        LoginAndRegisterGUIControllerCLI loginAndRegisterGUIController = new LoginAndRegisterGUIControllerCLI(currentSession, returningHome);
        loginAndRegisterGUIController.start();
        returningHome.setReturningHome(false);
        start();
    }

    private void searchStays() {
        String[] data = view.searchStay();

        String city = data[0].trim();
        String checkInStr = data[1].trim();
        String checkOutStr = data[2].trim();
        String guestsStr = data[3].trim();

        if (city.isEmpty() || checkInStr.isEmpty() || checkOutStr.isEmpty() || guestsStr.isEmpty()) {
            System.out.println("All fields are required. Please try again.");
            start();
            return;
        }

        LocalDate checkIn;
        LocalDate checkOut;
        int guests;

        try {
            checkIn = LocalDate.parse(checkInStr);
            checkOut = LocalDate.parse(checkOutStr);
        } catch (Exception e) {
            System.out.println("Dates must be in the format yyyy-MM-dd.");
            start();
            return;
        }

        LocalDate today = LocalDate.now();

        if (checkIn.isBefore(today)) {
            System.out.println("Check-in date cannot be in the past.");
            start();
            return;
        }

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out date must be after check-in date.");
            start();
            return;
        }

        try {
            guests = Integer.parseInt(guestsStr);
            if (guests <= 0) {
                System.out.println("Number of guests must be greater than zero.");
                start();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Guests must be a valid positive number.");
            start();
            return;
        }

        Session session = SessionManager.getSessionManager().getSessionFromId(currentSession);
        session.setCity(city);
        session.setCheckIn(checkIn);
        session.setCheckOut(checkOut);
        session.setNumGuests(guests);

        ListStaysGUIControllerCLI listStaysController = new ListStaysGUIControllerCLI(currentSession, returningHome);
        listStaysController.start();

        session.resetCity();
        session.resetCheckIn();
        session.resetCheckOut();
        session.resetNumGuests();

        returningHome.setReturningHome(false);
        start();
    }

}

