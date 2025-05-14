package org.example.models.npc;

import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Clint extends NPC{
    Marketplace marketplace;

    public Clint(String name){
        super(name);
    }


    public Clint(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift, Quest firstQuest, Quest secondQuest, Quest thirdQuest) {
        super(name, job, dialogues, favorites, itemsToGift, firstQuest, secondQuest, thirdQuest);
        this.marketplace = marketplace;
    }
}
