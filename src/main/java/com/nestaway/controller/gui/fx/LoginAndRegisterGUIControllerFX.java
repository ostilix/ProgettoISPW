package com.nestaway.controller.gui.fx;

import com.nestaway.bean.HostBean;
import com.nestaway.bean.UserBean;
import com.nestaway.controller.app.LoginController;
import com.nestaway.exception.DuplicateEntryException;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.fx.FilesFXML;
import com.nestaway.utils.view.fx.PageManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginAndRegisterGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Label loginMessage;

    @FXML
    Label signupMessage;

    @FXML
    TextField userLogin;

    @FXML
    PasswordField passLogin;

    @FXML
    TextField user;

    @FXML
    PasswordField password;

    @FXML
    TextField firstname;

    @FXML
    TextField lastname;

    @FXML
    TextField email;

    @FXML
    TextField paypal;

    @FXML
    public void loginAction() {
        resetMsg(errorMsg, loginMessage, signupMessage);

        String [] loginInfo = {userLogin.getText(), passLogin.getText()};
        if(loginInfo[0].isEmpty() || loginInfo[1].isEmpty()) {
            setMsg(loginMessage, "Please fill in all fields!");
            return;
        }
        try{
            UserBean userBean = new UserBean();
            userBean.setUsername(loginInfo[0]);
            userBean.setPassword(loginInfo[1]);
            LoginController loginController = new LoginController();
            userBean = loginController.login(userBean);
            PageManagerSingleton.getInstance().setHome(FilesFXML.HOST_HOME.getPath(), currentSession);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(userBean);
        } catch (IncorrectDataException | NotFoundException e) {
            setMsg(loginMessage, e.getMessage());
        } catch (OperationFailedException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }

    @FXML
    public void signupAction(){
        resetMsg(errorMsg, loginMessage, signupMessage);

        String [] strings = {firstname.getText(), lastname.getText(), email.getText(), paypal.getText(), user.getText(), password.getText()};
        if(strings[0].isEmpty() || strings[1].isEmpty() || strings[2].isEmpty() || strings[3].isEmpty() || strings[4].isEmpty() || strings[5].isEmpty()) {
            setMsg(signupMessage, "Please fill in all fields!");
            return;
        }
        try{
            HostBean hostBean = new HostBean();
            hostBean.setFirstName(strings[0]);
            hostBean.setLastName(strings[1]);
            hostBean.setEmailAddress(strings[2]);
            hostBean.setUsername(strings[3]);
            hostBean.setInfoPayPal(strings[4]);
            hostBean.setPassword(strings[5]);
            LoginController loginController = new LoginController();
            UserBean userBean = loginController.register(hostBean);
            PageManagerSingleton.getInstance().setHome(FilesFXML.HOST_HOME.getPath(), currentSession);
            SessionManager.getSessionManager().getSessionFromId(currentSession).setUser(userBean);
        } catch (IncorrectDataException | DuplicateEntryException e) {
            setMsg(signupMessage, e.getMessage());
        } catch (OperationFailedException | NotFoundException e) {
            setMsg(errorMsg,e.getMessage());
        }
    }


    @Override
    public void initialize(Integer session) throws OperationFailedException, NotFoundException {
        resetMsg(errorMsg, loginMessage, signupMessage);
        currentSession = session;
    }
}
