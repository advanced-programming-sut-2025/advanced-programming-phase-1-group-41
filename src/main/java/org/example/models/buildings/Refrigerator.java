package org.example.models.buildings;

import org.example.models.ObjectMap;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Refrigerator implements ObjectMap {
    public final ArrayList<Slot> slots;

    public Refrigerator() {
        this.slots = new ArrayList<>();
    }
}
