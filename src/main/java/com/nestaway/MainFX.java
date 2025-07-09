package com.nestaway;

import com.nestaway.exception.EncryptionException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.dao.DemoDataLoader;
import com.nestaway.utils.view.fx.FilesFXML;
import com.nestaway.utils.view.fx.PageManagerSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

    Integer currentSession;

    static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws OperationFailedException, NotFoundException {
        if ("DEMO".equalsIgnoreCase(System.getProperty("DAO_TYPE"))) {
            try {
                DemoDataLoader.load();
            } catch (EncryptionException e) {
                throw new OperationFailedException("Failed to load demo data");
            }
        }

        currentSession = SessionManager.getSessionManager().createSession();
        stage.setMaximized(true);
        PageManagerSingleton.getInstance(stage).setHome(FilesFXML.HOME.getPath(), currentSession);
    }

    @Override
    public void stop() {
        SessionManager.getSessionManager().removeSession(currentSession);
    }
}
