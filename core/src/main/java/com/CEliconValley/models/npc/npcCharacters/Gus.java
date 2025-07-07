package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Gus extends NPC{
    Marketplace marketplace;
    public Gus(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(86);setY(20);
        setDaysToUnlockQ3(2*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(190,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
