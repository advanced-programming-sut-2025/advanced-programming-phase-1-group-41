package org.example.models.npc;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Harvey extends NPC{
    NPCHome home;
    public Harvey(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        setX(2);setY(60);
    }
    @Override
    public String getChar(){
        return Colors.colorize(196,0,"웃 ");
    }

    public NPCHome getHome() {
        return home;
    }
}
