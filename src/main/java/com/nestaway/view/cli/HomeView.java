package com.nestaway.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HomeView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("HOME PAGE", "Search Stays", "Login or Register", "Exit");
        return getInputMenu(3);
    }

    public String[] searchStay() {
        printTitle("SEARCH STAY");
        String[] data = new String[4];
        Scanner input = new Scanner(System.in);

        getInput(input, data, 0, "Enter the name of the city: ");
        getInput(input, data, 1, "Enter check-in date (yyyy-mm-dd): ");
        getInput(input, data, 2, "Enter check-out date (yyyy-mm-dd): ");
        getInput(input, data, 3, "Enter number of guests: ");

        showMessage("DATA ENTERED");
        showMessage("City Name: " + data[0]);
        showMessage("Check-in Date: " + data[1]);
        showMessage("Check-out Date: " + data[2]);
        showMessage("Number of guests: " + data[3]);

        return data;
    }
}
