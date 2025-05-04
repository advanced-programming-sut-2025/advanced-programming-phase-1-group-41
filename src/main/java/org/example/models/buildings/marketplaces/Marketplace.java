package org.example.models.buildings.marketplaces;

import org.example.models.items.Slot;
import org.example.models.npc.NPC;

import java.util.ArrayList;

public abstract class Marketplace {
    protected NPC owner;
    public final ArrayList<Slot> itemsForSale;

    // TODO add the actual items in the constructor of
    // each marketplace

    public Marketplace() {
        this.itemsForSale = new ArrayList<>();
    }

    public NPC getOwner() {
        return owner;
    }
}
