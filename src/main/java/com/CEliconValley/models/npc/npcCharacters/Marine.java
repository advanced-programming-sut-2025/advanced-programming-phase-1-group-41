package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Marine extends NPC{
    Marketplace marketplace;
    public Marine(String name, Occupation job,  Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        setX(85);setY(9);
        setDaysToUnlockQ3(1*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(24,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
