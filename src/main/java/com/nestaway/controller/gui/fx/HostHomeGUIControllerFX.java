package com.nestaway.controller.gui.fx;

import com.nestaway.utils.view.fx.FilesFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HostHomeGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Button notifications;

    @FXML
    Button stays;

    @FXML
    Button settings;

    @FXML
    public void viewNotifications(){
        goNext(FilesFXML.NOTIFICATION.getPath());
    }

    @FXML
    public void viewStays(){
        setMsg(errorMsg,"Not implemented yet.");
    }

    @FXML
    public void viewSettings(){
        setMsg(errorMsg,"Not implemented yet.");
    }

    public void initialize(Integer session) {
        resetMsg(errorMsg);
        this.currentSession = session;
    }
}
