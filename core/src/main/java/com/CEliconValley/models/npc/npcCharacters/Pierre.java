package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

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
