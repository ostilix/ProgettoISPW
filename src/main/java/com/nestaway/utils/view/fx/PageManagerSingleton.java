package com.nestaway.utils.view.fx;

import com.nestaway.controller.gui.fx.AbstractGUIControllerFX;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageManagerSingleton {

    private final Stage primaryStage;
    private static final Deque<String> viewStack = new ArrayDeque<>();
    private static PageManagerSingleton instance = null;

    private PageManagerSingleton(Stage stage) {
        primaryStage = stage;
    }

    public static synchronized PageManagerSingleton getInstance(Stage ... stage){
        if(instance == null){
            instance = new PageManagerSingleton(stage[0]);
        }
        return instance;
    }

    public void goNext(String fxmlPath, Integer session)  throws OperationFailedException, NotFoundException {
        viewStack.push(fxmlPath);
        changeView(fxmlPath, session);
    }

    public void goBack(Integer session)  throws OperationFailedException, NotFoundException{
        if (!viewStack.isEmpty()) {
            viewStack.pop();
            String previousView = viewStack.peek();
            changeView(previousView, session);
        }
    }

    public void goHome(Integer session)  throws OperationFailedException, NotFoundException{
        if(!viewStack.isEmpty()){
            String home = viewStack.reversed().pop();
            setHome(home, session);
        }
    }

    public void setHome(String fxmlPath, Integer session)  throws OperationFailedException, NotFoundException{
        Deque<String> oldStack = new ArrayDeque<>(viewStack);
        try {
            viewStack.clear();
            viewStack.push(fxmlPath);
            changeView(fxmlPath, session);
        } catch (OperationFailedException | NotFoundException e){
            viewStack.clear();
            viewStack.addAll(oldStack);
            throw e;
        }
    }

    public void changeView(String fxmlPath, Integer session) throws OperationFailedException, NotFoundException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            AbstractGUIControllerFX controller = loader.getController();
            controller.initialize(session);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("NestAway");

            // Imposta dimensioni massime disponibili (full screen con bordi visibili)
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(screenBounds.getMinX());
            primaryStage.setY(screenBounds.getMinY());
            primaryStage.setWidth(screenBounds.getWidth());
            primaryStage.setHeight(screenBounds.getHeight());

            primaryStage.show();
        } catch (IOException | IllegalStateException | NullPointerException e) {
            viewStack.pop();
            String errorMsg = "Impossible to load the view: " + fxmlPath;
            Logger.getGlobal().log(Level.SEVERE, errorMsg, e);
            throw new OperationFailedException();
        } catch (OperationFailedException | NotFoundException e) {
            viewStack.pop();
            throw e;
        }
    }


}
