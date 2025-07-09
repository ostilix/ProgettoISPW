package com.nestaway.bean;

import com.nestaway.exception.IncorrectDataException;

public class UserBean {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IncorrectDataException {
        if(username == null || username.isEmpty()) {
            throw new IncorrectDataException("Username cannot be empty");
        } else if (username.length() > 10) {
            throw new IncorrectDataException("Too long username (max 10 characters)");
        }else{
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
