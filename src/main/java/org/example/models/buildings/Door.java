package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class Door implements Building {
    @Override
    public String getChar() {
        if(isClosed){
            return Colors.colorize(196,0,"[]");
        }
        return Colors.colorize(82,0,"][");
    }

    @Override
    public String getName() {
        return "Door";
    }

    boolean isClosed = false;

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
