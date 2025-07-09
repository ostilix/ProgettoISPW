package com.nestaway.controller.gui.cli;

import com.nestaway.bean.HostBean;
import com.nestaway.bean.UserBean;
import com.nestaway.controller.app.LoginController;
import com.nestaway.exception.DuplicateEntryException;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;
import com.nestaway.view.cli.LoginAndRegisterView;

public class LoginAndRegisterGUIControllerCLI extends AbstractGUIControllerCLI {

    private final LoginAndRegisterView view = new LoginAndRegisterView();

    public LoginAndRegisterGUIControllerCLI(Integer session, ReturningHome returningHome){
        this.currentSession = session;
        this.returningHome = returningHome;
    }

    public void start(){
        int choice;
        choice = view.showMenu();

        switch(choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> goHome();
            case 4 -> exit();
            default -> throw new IllegalArgumentException("Invalid case!");
        }
    }

    private void login() {
        try {
            String [] loginInfo = view.login();
            if(loginInfo[0].isEmpty() || loginInfo[1].isEmpty()) {
                view.showMessage("Please fill in all fields!");
            }
            UserBean user = new UserBean();
            user.setUsername(loginInfo[0]);
            user.setPassword(loginInfo[1]);
            LoginController loginController = new LoginController();
            user = loginController.login(user);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(user);
            HostHomeGUIControllerCLI organizerHomeGUIController = new HostHomeGUIControllerCLI(currentSession,returningHome);
            organizerHomeGUIController.start();
        } catch (IncorrectDataException | NotFoundException e) {
            view.showMessage(e.getMessage());
        } catch (OperationFailedException e) {
            view.showError(e.getMessage());
        }
        if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }

    private void register() {
        try {
            String [] registerInfo = view.register();
            if(registerInfo[0].isEmpty() || registerInfo[1].isEmpty() || registerInfo[2].isEmpty() || registerInfo[3].isEmpty() || registerInfo[4].isEmpty() || registerInfo[5].isEmpty()) {
                view.showMessage("Please fill in all fields!");
            }
            HostBean host = new HostBean();
            host.setFirstName(registerInfo[0]);
            host.setLastName(registerInfo[1]);
            host.setEmailAddress(registerInfo[2]);
            host.setUsername(registerInfo[3]);
            host.setInfoPayPal(registerInfo[4]);
            host.setPassword(registerInfo[5]);
            LoginController loginController = new LoginController();
            UserBean user = loginController.register(host);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(user);
            HostHomeGUIControllerCLI hostHomeGUIController = new HostHomeGUIControllerCLI(currentSession, returningHome);
            hostHomeGUIController.start();
        } catch (IncorrectDataException e) {
            view.showMessage(e.getMessage());
        } catch (OperationFailedException | DuplicateEntryException e) {
            view.showError(e.getMessage());
        }
        if (Boolean.FALSE.equals(returningHome.getReturningHome())) {
            start();
        }
    }
}

