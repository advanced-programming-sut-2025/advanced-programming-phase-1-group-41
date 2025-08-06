package com.CEliconValley.common.messages;

public class PosDiff {
    int x;
    int y;
    String playername;

    public PosDiff(String playername, int x, int y) {
        this.playername = playername;
        this.x = x;
        this.y = y;
    }
}
