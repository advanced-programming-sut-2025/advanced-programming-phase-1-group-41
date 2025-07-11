package com.CEliconValley.models.buildings;

import com.CEliconValley.models.Colors;

public class Door implements Building {
    @Override
    public String getChar() {
        if(isClosed){
            return Colors.colorize(196,0,"[]");
        } else if(closesSoon) {
            return Colors.colorize(208, 0, "||");
        }
        return Colors.colorize(82,0,"][");
    }

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
}
