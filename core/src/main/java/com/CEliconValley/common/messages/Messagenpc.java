package com.CEliconValley.common.messages;

import dev.morphia.annotations.Embedded;

@Embedded
public class Messagenpc {
    public boolean isNPC;
    public String message;

    public Messagenpc(boolean isNPC, String message) {
        this.isNPC = isNPC;
        this.message = message;
    }
}
