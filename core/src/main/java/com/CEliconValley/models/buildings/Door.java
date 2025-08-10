package com.CEliconValley.models.buildings;

import com.CEliconValley.models.ui.TerminalColors;

public class Door implements Building {
    @Override
    public String getChar() {
        if(isClosed){
            return TerminalColors.colorize(196,0,"[]");
        } else if(closesSoon) {
            return TerminalColors.colorize(208, 0, "||");
        }
        return TerminalColors.colorize(82,0,"][");
    }
    private int initialize=-1;

    @Override
    public String getName() {
        return "Door";
    }

    boolean isClosed = false;
    boolean closesSoon = false;

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosesSoon(boolean closesSoon) {
        this.closesSoon = closesSoon;
    }

    @Override
    public int getAnchorX() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getAnchorY() {
        return 0;
    }

    public int getInitialize(){
        return initialize;
    }
    public void setInitialize(int initialize) {
        this.initialize = initialize;
    }
}
