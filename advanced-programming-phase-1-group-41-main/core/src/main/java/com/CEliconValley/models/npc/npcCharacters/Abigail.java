package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.npc.npchomes.NPCHome;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Abigail extends NPC{
    NPCHome home;

    public Abigail(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.home = home;
        setDaysToUnlockQ3(4*28);
        setX(26);setY(43);
    }
    @Override
    public String getChar(){
        return Colors.colorize(93,0,"웃 ");
    }

    public NPCHome getHome() {
        return home;
    }
}
