package com.CEliconValley.common.messages;

public class LoginCred extends Message{
    public String username;
    public String password;

    public LoginCred(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
