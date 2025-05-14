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
    public ArrayList<Item> favorites;
    public ArrayList<Slot> ItemsToGift;

    private Quest firstQuest;
    private Quest secondQuest;
    private Quest thirdQuest;

    public NPC(String name) {
        this.name = name;
    }

    public NPC(String name, Occupation job, ArrayList<String> dialogues,
               ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,
               Quest firstQuest, Quest secondQuest, Quest thirdQuest) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.favorites = favorites;
        this.ItemsToGift = itemsToGift;
        this.firstQuest = firstQuest;
        this.secondQuest = secondQuest;
        this.thirdQuest = thirdQuest;
    }

    public String getName() {
        return name;
    }

    public Occupation getJob() {
        return job;
    }

    public NPCHome getHome() {
        return home;
    }

    public String getDialogues() {
        return dialogues.get(new Random().nextInt(dialogues.size()));
    }

    public Quest getFirstQuest() {
        return firstQuest;
    }

    public Quest getSecondQuest() {
        return secondQuest;
    }

    public Quest getThirdQuest() {
        return thirdQuest;
    }
}
