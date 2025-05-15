package org.example.models.npc.npcCharacters;

import org.example.models.Colors;
import org.example.models.Occupation;
import org.example.models.npc.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Abigail extends NPC{
    NPCHome home;

    public Abigail(String name, Occupation job, NPCHome home, ArrayList<String> dialogues, ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,ArrayList<Quest> quests) {
        super(name, job, dialogues, favorites, itemsToGift, quests);
        this.home = home;
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
