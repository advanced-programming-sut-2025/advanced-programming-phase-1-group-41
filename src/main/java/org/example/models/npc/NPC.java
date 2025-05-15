package org.example.models.npc;

import org.example.models.Occupation;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;
import java.util.Random;

public abstract class NPC {


    private String name;
    private Occupation job;
    private NPCHome home;
    private ArrayList<String> dialogues;
    private ArrayList<Item> favorites;
    private ArrayList<Slot> ItemsToGift;
    private ArrayList<Quest> quests;
    private int x=-10;
    private int y=-10;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getChar(){
        return "";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public NPC(String name, Occupation job, ArrayList<String> dialogues,
               ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,
               ArrayList<Quest> quests) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.favorites = favorites;
        this.ItemsToGift = itemsToGift;
        this.quests = quests;

    }

    public String getName() {
        return name;
    }

    public Occupation getJob() {
        return job;
    }

    public String getDialogues() {
        return dialogues.get(new Random().nextInt(dialogues.size()));
    }



}
