package com.CEliconValley.common.messages;

public class PosDiff {
    public int x;
    public int y;
    public String playername;

    public PosDiff(String playername, int x, int y) {
        this.playername = playername;
        this.x = x;
        this.y = y;
    }
}
