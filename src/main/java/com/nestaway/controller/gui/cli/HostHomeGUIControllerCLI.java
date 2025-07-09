package com.nestaway.controller.gui.cli;

import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.HostHomeView;

public class HostHomeGUIControllerCLI extends AbstractGUIControllerCLI {

    private final HostHomeView view = new HostHomeView();

    public HostHomeGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start(){
        int choice;
        choice = view.showMenu();

        switch(choice) {
            case 1 -> stay();
            case 2 -> notif();
            case 3 -> setting();
            case 4 -> logout();
            case 5 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void stay() {
        view.showError("View Stays not implemented yet!");
        if(Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

    private void notif() {
        NotificationsGUIControllerCLI notificationsGUIController = new NotificationsGUIControllerCLI(currentSession, returningHome);
        notificationsGUIController.start();
        if(Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

    private void setting() {
        view.showError("View Settings not implemented yet!");
        if(Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }
}

