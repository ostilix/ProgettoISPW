package com.nestaway.view.cli;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class NotificationsView extends AbstractView{

    @Override
    public int showMenu() {
        printMenu("NOTIFICATIONS PAGE", "View Notifications", "Delete", "Home", "Log Out", "Exit");
        return getInputMenu(5);
    }

    public void showNotifications(String[] notifications) {
        printTitle("NOTIFICATIONS");
        for (String s : notifications) {
            showMessage(s);
        }
    }

    public List<Integer> deleteNotification() {

        List<Integer> notifs = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        showMessage("Do you want to delete all notification? (Y/N)");
        String choice = input.nextLine();
        if (choice.equals("Y")) {
            notifs.add(-1);
            return notifs;
        }

        int inputNumber;

        while (true){
            try {
                showMessage("Enter the number of the notification you want to delete: ");
                inputNumber = Integer.parseInt(input.nextLine());
                if (inputNumber <= 0) {
                    throw new InputMismatchException();
                }

                notifs.add(inputNumber);

                showMessage("Do you want to delete another notification? (Y/N): ");
                choice = input.nextLine();
                if (choice.equals("N")) {
                    break;
                }
            } catch (InputMismatchException e) {
                showMessage("Invalid input!");
                input.next();
            }
        }
        return notifs;
    }
}

