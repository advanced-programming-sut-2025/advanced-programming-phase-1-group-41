package com.CEliconValley.common.messages;

import java.util.HashMap;
import java.util.Objects;

public class SuccessMessage extends Message{
    public String success;
    public String type;
    public SuccessMessage(String type, String success) {
        this.success = success;
        this.type = type;
    }
}
