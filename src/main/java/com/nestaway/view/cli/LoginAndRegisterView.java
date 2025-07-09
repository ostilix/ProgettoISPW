package com.nestaway.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginAndRegisterView extends AbstractView {

    public int showMenu() {
        printMenu("LOGIN PAGE", "Login", "Register", "Home", "Exit");
        return getInputMenu(4);
    }

    public String[] login() {

        printTitle("LOGIN PROCEDURE");
        String[] credentials = new String[2];
        Scanner input = new Scanner(System.in);
        getInput(input, credentials, 0, "Enter your Username: ");
        getInput(input, credentials, 1, "Enter your Password: ");
        return credentials;
    }

    public String[] register() {

        printTitle("REGISTRATION PROCEDURE");
        String[] user = new String[6];
        Scanner input = new Scanner(System.in);
        getInput(input, user, 0, "Enter your Firstname: ");
        getInput(input, user, 1, "Enter your Lastname: ");
        getInput(input, user, 2, "Enter your Email: ");
        getInput(input, user, 3, "Enter your Username: ");
        getInput(input, user, 4, "Enter your PayPal Email: ");
        getInput(input, user, 5, "Enter your Password: ");

        showMessage("DATA INSERTED CHECK - PLEASE CONFIRM");
        showMessage("Firstname: " + user[0]);
        showMessage("Lastname: " + user[1]);
        showMessage("Email: " + user[2]);
        showMessage("PayPal Email: " + user[3]);
        showMessage("Username: " + user[4]);
        showMessage("Password: " + user[5]);

        int choice;
        while (true) {
            try {
                showMessage("Press 0 to confirm or 1 to cancel: ");
                choice = input.nextInt();
                if (choice >= 0 && choice <= 1) {
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                showMessage("Invalid input!");
                input.next();
            }
        }
        if (choice == 0) {
            return user;
        } else {
            return new String[0];
        }
    }
}

