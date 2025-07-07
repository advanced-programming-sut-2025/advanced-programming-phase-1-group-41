package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Willy extends NPC{
    Marketplace marketplace;
    public Willy(String name, Occupation job, Marketplace marketplace, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift, ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.marketplace = marketplace;
        setX(19);setY(1);
        setDaysToUnlockQ3(28*3);
    }
    @Override
    public String getChar(){
        return "\u001B[48;2;101;67;33m\u001B[38;5;15m웃 \u001B[0m";
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}
