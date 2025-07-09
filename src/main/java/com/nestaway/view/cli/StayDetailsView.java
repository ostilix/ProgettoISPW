package com.nestaway.view.cli;

public class StayDetailsView extends AbstractView{

    @Override
    public int showMenu() {
        printMenu("STAY DETAILS PAGE", "Show all info", "Show reviews", "Book stay", "Booking management", "Back", "Home", "Exit");
        return getInputMenu(7);
    }

    public void showInfo(String[] info) {
        printTitle("STAY INFO");
        for (String s : info) {
            showMessage(s);
        }
    }

    public void showReviews(String[] reviews) {
        printTitle("STAY REVIEWS");
        for (String s : reviews) {
            showMessage(s);
        }
    }
}
