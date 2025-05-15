package org.example.models.npc.npcCharacters;

import org.example.models.Occupation;
import org.example.models.npc.npchomes.NPCHome;
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
    private int friendShip;
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
        this.friendShip = 0;

    }

    public int getFriendShip() {
        return friendShip;
    }

    public void incFriendShip(int friendShip) {
        this.friendShip =+ friendShip;
    }

    public String getName() {
        return name;
    }

    public Occupation getJob() {
        return job;
    }

    public String getDialogues(int i) {
        return dialogues.get(i);
    }



}
