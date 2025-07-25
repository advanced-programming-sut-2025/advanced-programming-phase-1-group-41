package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.npc.npchomes.NPCHome;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Robin extends NPC{
    Marketplace marketplace;
    NPCHome home;
    public Robin(String name, Occupation job, NPCHome home, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        this.home = home;
        setX(70);setY(13);
        setDaysToUnlockQ3(11*28);
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
