package org.example.models.npc.npcCharacters;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Pierre extends NPC{
    Marketplace marketplace;
    public Pierre(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        setX(45);setY(10);
        setDaysToUnlockQ3(2*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(15,105,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
