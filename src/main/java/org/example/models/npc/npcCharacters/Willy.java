package org.example.models.npc.npcCharacters;

import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Willy extends NPC{
    Marketplace marketplace;
    public Willy(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift, ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(19);setY(1);
        setDaysToUnlockQ3(28*3);
    }
    @Override
    public String getChar(){
        return "\u001B[48;2;101;67;33m\u001B[38;5;15m웃 \u001B[0m";
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
