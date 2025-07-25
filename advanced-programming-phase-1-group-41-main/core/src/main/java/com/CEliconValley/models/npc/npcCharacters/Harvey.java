package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.npc.npchomes.NPCHome;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Harvey extends NPC{
    NPCHome home;
    public Harvey(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        setX(60);setY(55);
        setDaysToUnlockQ3(12*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(239,0,"웃 ");
    }

    public NPCHome getHome() {
        return home;
    }
}
