package com.nestaway.controller.gui.cli;

import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.cli.ReturningHome;

public abstract class AbstractGUIControllerCLI {

    protected Integer currentSession;

    protected ReturningHome returningHome;

    public abstract void start();

    protected void exit(){
        SessionManager.getSessionManager().removeSession(currentSession);
        System.out.println("Exiting...Bye bye!");
        System.exit(0);
    }

    protected void goBack() {
        returningHome.setReturningHome(false);
    }

    protected void goHome() {
        SessionManager.getSessionManager().getSessionFromId(currentSession).softReset();
        returningHome.setReturningHome(true);
    }

    protected void logout() {
        SessionManager.getSessionManager().getSessionFromId(currentSession).reset();
        returningHome.setReturningHome(true);
    }
}

