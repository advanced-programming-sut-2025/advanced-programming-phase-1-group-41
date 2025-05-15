package org.example.models.npc;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import javax.crypto.MacSpi;
import java.util.ArrayList;

public class Willy extends NPC{
    Marketplace marketplace;
    public Willy(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift, ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(19);setY(1);
    }
    @Override
    public String getChar(){
        return Colors.colorize(0,15,":/");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
