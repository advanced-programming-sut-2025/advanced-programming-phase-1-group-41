package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.npc.npchomes.NPCHome;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Lia extends NPC{
    NPCHome home;
    public Lia(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift,quests);
        this.home = home;setX(11);setY(53);
        setDaysToUnlockQ3(7*28);
    }
    @Override
    public String getChar(){
        return Colors.colorize(208,0,"웃 ");
    }

    public NPCHome getHome() {
        return home;
    }
}
