package com.nestaway;

import com.nestaway.controller.gui.cli.HomeGUIControllerCLI;
import com.nestaway.exception.EncryptionException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.dao.DemoDataLoader;
import com.nestaway.utils.view.cli.ReturningHome;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainCLI {
    private static final Logger logger = Logger.getLogger(MainCLI.class.getName());

    private MainCLI() {
        throw new IllegalStateException("Starter class");
    }

    static void run() {
        if ("DEMO".equalsIgnoreCase(System.getProperty("DAO_TYPE"))) {
            try {
                DemoDataLoader.load();
            } catch (EncryptionException e) {
                logger.log(Level.SEVERE, "Error loading demo data", e); // ✅ Log al posto di System.err
            }
        }

        Integer currentSession = SessionManager.getSessionManager().createSession();
        HomeGUIControllerCLI controller = new HomeGUIControllerCLI(currentSession, new ReturningHome());
        controller.start();
    }
}
