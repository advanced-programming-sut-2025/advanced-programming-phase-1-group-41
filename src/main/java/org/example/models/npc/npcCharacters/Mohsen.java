package org.example.models.npc.npcCharacters;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.npc.npchomes.NPCHome;

import java.util.ArrayList;

public class Mohsen extends NPC{
    Marketplace marketplace;
    public Mohsen(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift, ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(50);setY(32);
        setDaysToUnlockQ3(1);
    }
    @Override
    public String getChar(){
        return Colors.colorize(255,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
