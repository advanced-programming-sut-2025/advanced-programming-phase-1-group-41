package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class Wall implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,"##");
    }
    //TODO
}
