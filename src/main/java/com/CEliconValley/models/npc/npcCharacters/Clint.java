package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Clint extends NPC{
    Marketplace marketplace;




    public Clint(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(86);setY(55);
        setDaysToUnlockQ3(5*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(196,0,"웃 ");
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
