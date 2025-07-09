package com.nestaway;

import com.nestaway.controller.gui.cli.HomeGUIControllerCLI;
import com.nestaway.exception.EncryptionException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.dao.DemoDataLoader;
import com.nestaway.utils.view.cli.ReturningHome;

public class MainCLI {

    private MainCLI() {
        throw new IllegalStateException("Starter class");
    }

    static void run() {
        if ("DEMO".equalsIgnoreCase(System.getProperty("DAO_TYPE"))) {
            try {
                DemoDataLoader.load();
            } catch (EncryptionException e) {
                System.err.println("Error loading demo data: " + e.getMessage());
            }
        }

        Integer currentSession = SessionManager.getSessionManager().createSession();
        HomeGUIControllerCLI controller = new HomeGUIControllerCLI(currentSession, new ReturningHome());
        controller.start();
    }
}
