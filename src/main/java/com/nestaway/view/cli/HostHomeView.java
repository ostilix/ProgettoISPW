package com.nestaway.view.cli;

public class HostHomeView extends AbstractView{

    @Override
    public int showMenu() {
        printMenu("HOST HOMEPAGE", "View Stays", "View Notifications", "View Settings", "Log Out", "Exit");
        return getInputMenu(5);
    }
}
