package org.example.models.npc.npcCharacters;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.npc.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Robin extends NPC{
    Marketplace marketplace;
    NPCHome home;
    public Robin(String name, Occupation job, NPCHome home, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        this.home = home;
        setX(70);setY(13);
    }
    @Override
    public String getChar(){
        return Colors.colorize(198,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
    public NPCHome getHome() {
        return home;
    }
}
