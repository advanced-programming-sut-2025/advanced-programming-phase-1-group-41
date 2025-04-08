package models.buildings.marketplaces;

import models.items.Slot;
import models.npc.NPC;

import java.util.ArrayList;

public abstract class Marketplace {
    NPC owner;
    ArrayList<Slot> itemsForSale;

    // TODO add the actual items in the constructor of
    // each marketplace

    public Marketplace() {
        this.itemsForSale = new ArrayList<>();
    }
}
