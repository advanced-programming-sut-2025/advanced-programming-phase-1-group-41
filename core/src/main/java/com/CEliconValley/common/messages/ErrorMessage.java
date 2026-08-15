package com.CEliconValley.common.messages;

public class ErrorMessage extends Message{
    public String error;
    public String type;

    public ErrorMessage(String type, String message) {
        this.error = message;
        this.type = type;
    }
}
