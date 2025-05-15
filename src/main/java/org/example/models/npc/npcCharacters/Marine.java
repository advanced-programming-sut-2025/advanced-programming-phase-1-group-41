package org.example.models.npc.npcCharacters;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Marine extends NPC{
    Marketplace marketplace;
    public Marine(String name, Occupation job,  Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        setX(85);setY(9);
    }
    @Override
    public String getChar(){
        return Colors.colorize(24,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
