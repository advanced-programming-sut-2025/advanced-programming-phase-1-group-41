package models.npc;

import models.Occupation;
import models.buildings.marketplaces.Marketplace;
import models.buildings.npchomes.NPCHome;
import models.items.Item;
import models.items.Slot;

import java.util.ArrayList;

public enum NPC {
    Sebastien,
    Abigail,
    Harvey,
    Lia,
    Robin
    // TODO
    ;

    private String name;
    private Occupation job;
    private NPCHome home;
    private Marketplace marketplace;
    private String dialogues;
    public ArrayList<Item> favorites;
    public ArrayList<Slot> ItemsToGift;

    private Quest firstQuest;
    private Quest secondQuest;
    private Quest thirdQuest;

    NPC(String name, Occupation job, NPCHome home,
        Marketplace marketplace, String dialogues,
        ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,
        Quest firstQuest, Quest secondQuest, Quest thirdQuest) {
        this.name = name;
        this.job = job;
        this.home = home;
        this.marketplace = marketplace;
        this.dialogues = dialogues;
        this.favorites = favorites;
        ItemsToGift = itemsToGift;
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

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public String getDialogues() {
        return dialogues;
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
