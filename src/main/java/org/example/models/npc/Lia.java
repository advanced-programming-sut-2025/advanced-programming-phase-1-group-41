package org.example.models.npc;

import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Lia extends NPC{
    NPCHome home;
    public Lia(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift,quests);
        this.home = home;
    }
    public NPCHome getHome() {
        return home;
    }
}
