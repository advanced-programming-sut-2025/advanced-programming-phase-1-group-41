package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Refrigerator implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(15,15,"[]");
    }
    public final ArrayList<Slot> slots;

    public Refrigerator() {
        this.slots = new ArrayList<>();
    }
}
