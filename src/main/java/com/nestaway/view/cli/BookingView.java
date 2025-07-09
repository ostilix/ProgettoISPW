package com.nestaway.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BookingView extends AbstractView {

    @Override
    public int showMenu() {
        printMenu("BOOKING PAGE", "Show all availabilities for the selected stay", "Book the selected stay", "Back", "Home", "Exit");
        return getInputMenu(5);
    }

    public void showAvailability(String[] availability) {
        printTitle("AVAILABILITY LIST");
        for (String day : availability) {
            showMessage(day);
        }
    }

    public String[] insertBookingData() {
        printTitle("NEW BOOKING PROCEDURE");
        String[] data = new String[5];
        Scanner input = new Scanner(System.in);

        getInput(input, data, 0, "Enter your Firstname: ");
        getInput(input, data, 1, "Enter your Lastname: ");
        getInput(input, data, 2, "Enter your Email: ");
        getInput(input, data, 3, "Enter your Telephone Number: ");
        getInput(input, data, 4, "Do you want to pay online using PayPal? (Y/N): ");

        showMessage("DATA ENTERED - PLEASE CONFIRM");
        showMessage("Firstname: " + data[0]);
        showMessage("Lastname: " + data[1]);
        showMessage("Email: " + data[2]);
        showMessage("Telephone Number: " + data[3]);
        showMessage("PayPal: " + data[4]);

        if (data[4].equalsIgnoreCase("Y")) {
            data[4] = "true";
        } else {
            data[4] = "false";
        }

        int choice;
        while (true) {
            try {
                showMessage("Press 0 to confirm or 1 to cancel: ");
                choice = input.nextInt();
                if (choice == 0 || choice == 1) {
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                showError("Invalid input! Please enter 0 or 1.");
                input.next();
            }
        }
        if (choice == 0) {
            return data;
        } else {
            return new String[0];
        }
    }
}
