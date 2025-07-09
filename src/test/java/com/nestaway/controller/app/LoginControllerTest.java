package com.nestaway.controller.app;

import com.nestaway.bean.HostBean;
import com.nestaway.bean.UserBean;
import com.nestaway.exception.IncorrectDataException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.exception.DuplicateEntryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void testLogin() throws IncorrectDataException, OperationFailedException, NotFoundException {
        System.setProperty("DAO_TYPE", "JDBC");
        LoginController loginController = new LoginController();
        UserBean userBean = new UserBean();
        userBean.setUsername("mariorossi");
        userBean.setPassword("mariorossi");
        UserBean user = loginController.login(userBean);
        assertEquals("mariorossi", user.getUsername());
    }

    @Test
    void testRegister() throws IncorrectDataException, OperationFailedException, DuplicateEntryException{
        System.setProperty("DAO_TYPE", "JDBC");
        HostBean hostBean = new HostBean();
        hostBean.setUsername("pieroneri");
        hostBean.setPassword("pieroneri");
        hostBean.setEmailAddress("piero.neri@gmail.com");
        hostBean.setFirstName("Piero");
        hostBean.setLastName("Nero");
        hostBean.setInfoPayPal("piero.neri.paypal@gmail.com");
        LoginController loginController = new LoginController();
        UserBean user = loginController.register(hostBean);
        assertEquals("pieroneri", user.getUsername());
        assertNotEquals("pieroneri", user.getPassword());
    }
}
