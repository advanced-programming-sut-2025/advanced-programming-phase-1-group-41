package com.CEliconValley.common.messages;

public class JoinLobbyCred {
    public String username;
    public String id;
    public String password;

    public JoinLobbyCred(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }



}
