package com.nestaway;

import com.nestaway.exception.EncryptionException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.dao.DemoDataLoader;
import com.nestaway.utils.dao.factory.TypeDAO;
import com.nestaway.utils.view.fx.FilesFXML;
import com.nestaway.utils.view.fx.PageManagerSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainDEMO extends Application {

    Integer currentSession;

    static void run(String[] args) {
        System.setProperty("DAO_TYPE", TypeDAO.DEMO.name());
        launch(args);
    }

    @Override
    public void start(Stage stage) throws OperationFailedException, NotFoundException {
        try {
            DemoDataLoader.load();
        } catch (EncryptionException e) {
            throw new OperationFailedException();
        }

        currentSession = SessionManager.getSessionManager().createSession();
        PageManagerSingleton.getInstance(stage);
        PageManagerSingleton.getInstance().setHome(FilesFXML.HOME.getPath(), currentSession);
    }

    @Override
    public void stop() {
        SessionManager.getSessionManager().removeSession(currentSession);
    }
}
