package com.CEliconValley.models;

public class Chat {
    private Player from;
    private Player to;
    private String message;

    public Chat(Player from, Player to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
