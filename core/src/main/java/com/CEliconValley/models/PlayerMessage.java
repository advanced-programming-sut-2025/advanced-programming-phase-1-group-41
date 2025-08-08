package com.CEliconValley.models;

public class PlayerMessage {
    private String message;
    private String sender;

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public PlayerMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
