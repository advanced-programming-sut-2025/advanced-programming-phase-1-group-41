package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.npc.npchomes.NPCHome;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Sebastien extends NPC{
    NPCHome home;
    public Sebastien(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.home = home;
        setX(16);setY(55);
        setDaysToUnlockQ3(7);
    }
    @Override
    public String getChar(){
        return Colors.colorize(82,0,"웃 ");
    }

    public NPCHome getHome() {
        return home;
    }
}
