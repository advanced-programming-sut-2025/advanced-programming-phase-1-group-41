package com.CEliconValley.common.messages;

public class PreRegisterCred {
    public String username;
    public String password;
    public String confirm;
    public String nickname;
    public String email;

    public PreRegisterCred(String username, String password, String confirm, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.confirm = confirm;
        this.nickname = nickname;
        this.email = email;
    }

    public String getConfirm() {
        return confirm;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
