package com.CEliconValley.common.messages;

public class Position {
    public String playername;
    public int x;
    public int y;
    public int targetx;
    public int targety;
    public float renderx;
    public float rendery;
    public int currentdirection;
    public boolean ismoving;

    public Position(int currentdirection, boolean ismoving, String playername, float renderx, float rendery, int targetx, int targety, int x, int y) {
        this.currentdirection = currentdirection;
        this.ismoving = ismoving;
        this.playername = playername;
        this.renderx = renderx;
        this.rendery = rendery;
        this.targetx = targetx;
        this.targety = targety;
        this.x = x;
        this.y = y;
    }
}
