package com.nestaway.controller.gui.cli;

import com.nestaway.bean.StayBean;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.StayDetailsView;

public class StayDetailsGUIControllerCLI extends AbstractGUIControllerCLI {

    private final StayDetailsView stayDetailsView = new StayDetailsView();
    private StayBean stay;

    public StayDetailsGUIControllerCLI(Integer session, ReturningHome returningHome) {
        this.currentSession = session;
        this.stay = SessionManager.getSessionManager().getSessionFromId(session).getStay();
        this.returningHome = returningHome;
    }

    @Override
    public void start() {
        int choice;
        choice = stayDetailsView.showMenu();

        switch (choice) {
            case 1 -> showInfo();
            case 2 -> showReviews();
            case 3 -> bookStay();
            case 4 -> bookingManagement();
            case 5 -> goBack();
            case 6 -> goHome();
            case 7 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void showInfo() {
        String[] info = {
                "Stay Name: " + stay.getName(),
                "Description:" + stay.getDescription(),
                "City: " + stay.getCity(),
                "Address: " + stay.getAddress(),
                "Price per Night: " + stay.getPricePerNight() + " €",
                "Max Guests: " + stay.getMaxGuests(),
                "Number of Rooms: " + stay.getNumRooms(),
                "Number of Bathrooms: " + stay.getNumBathrooms(),
                "Host Username:\n " + stay.getHostUsername()
        };
        stayDetailsView.showInfo(info);
        start();
    }

    private void showReviews() {
        if (stay.getReviews() == null || stay.getReviews().isEmpty()) {
            stayDetailsView.showReviews(new String[]{"No reviews available for this stay."});
        } else {
            String[] reviewsFormatted = new String[stay.getReviews().size()];
            int i = 0;
            for (var review : stay.getReviews()) {
                reviewsFormatted[i] = String.format(
                        "Rating: %d/5\nComment: %s\nDate: %s\n",
                        review.getRating(),
                        review.getComment(),
                        review.getDate().toString()
                );
                i++;
            }
            stayDetailsView.showReviews(reviewsFormatted);
        }
        start();
    }

    private void bookStay() {
        StayBean stayBean = SessionManager.getSessionManager().getSessionFromId(currentSession).getStay();
        if (stayBean == null) {
            stayDetailsView.showError("No stay selected for booking.");
            start();
            return;
        }

        BookingGUIControllerCLI bookingGUIController = new BookingGUIControllerCLI(currentSession, returningHome);
        bookingGUIController.start();

        if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

    private void bookingManagement() {
        stayDetailsView.showError("Booking management is not implemented yet!");
        start();
    }
}
