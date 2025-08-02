package com.CEliconValley.common.messages;

public class LeaveLobbyCred {
    public String username;
    public String id;

    public LeaveLobbyCred(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
