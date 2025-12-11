package com.CEliconValley.common.messages;

public class HugCred {
    public boolean straight;
    public int direction;
    public String name;
    public HugCred(int direction, boolean straight, String name) {
        this.direction = direction;
        this.straight = straight;
        this.name = name;
    }
}
