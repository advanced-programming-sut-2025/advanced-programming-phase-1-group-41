package com.CEliconValley.common;

import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.common.messages.Messagenpc;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.CEliconValley.models.npc.npcCharacters.Quest;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Embedded
public class NPCData {
    HashMap<String,Integer> friendShipData;
    HashMap<String,Boolean> isTalkedTodayData;
    HashMap<String,Boolean> isGiftedTodayData;
    int daysToUnlockQ3;
    String name;
    Occupation job;
    String homename;
    ArrayList<String> dialogues;
    ArrayList<String> favoriteItems;
    ArrayList<SlotData> itemsToGive;
    ArrayList<QuestData> questsdata;
    ArrayList<Talk> talks;
    public int x;
    public int y;
    public boolean isOutside;
    public int randomX;
    public int randomY;
    public float renderX;
    public float renderY;
    public boolean randomSetter;
    public boolean isMoving;
    public int currentDirection;

    public NPCData() {
    }

    public NPCData(NPC npc) {
        friendShipData = new HashMap<>();
        isTalkedTodayData = new HashMap<>();
        isGiftedTodayData = new HashMap<>();
        this.daysToUnlockQ3 = npc.getDaysToUnlockQ3();
        for (Player player : npc.getFriendShip().keySet()) {
            friendShipData.put(player.getUser().getUsername(), npc.getFriendShip().get(player));
        }
        for (Player player : npc.getIsTalkedToday().keySet()) {
            isTalkedTodayData.put(player.getUser().getUsername(), npc.getIsTalkedToday().get(player));
        }
        for (Player player : npc.getIsGiftedToday().keySet()) {
            isGiftedTodayData.put(player.getUser().getUsername(), npc.getIsGiftedToday().get(player));
        }
        this.name = npc.getName();
        this.job = npc.getJob();
        this.homename = npc.getHome() == null ? null : npc.getHome().getName();
        this.dialogues = npc.getDialogues();
        this.favoriteItems = new ArrayList<>();
        for (Item favorite : npc.getFavorites()) {
            favoriteItems.add(favorite.getName());
        }
        this.itemsToGive = new ArrayList<>();
        for (Slot slot : npc.getItemsToGift()) {
            itemsToGive.add(new SlotData(slot));
        }
        this.questsdata = new ArrayList<>();
        for (Quest quest : npc.getQuests()) {
            questsdata.add(new QuestData(quest));
        }
        this.x = npc.x;
        this.y = npc.y;
        this.randomX = npc.randomX;
        this.randomY = npc.randomY;
        this.renderX = npc.renderX;
        this.renderY = npc.renderY;
        this.randomSetter = npc.randomSetter;
        this.isMoving = npc.isMoving;
        this.currentDirection = npc.currentDirection;
        this.isOutside = npc.isOutside;
        this.talks = new ArrayList<>(npc.getTalks());
    }

    public int getDaysToUnlockQ3() {
        return daysToUnlockQ3;
    }

    public HashMap<String, Integer> getFriendShipData() {
        return friendShipData;
    }

    public HashMap<String, Boolean> getIsGiftedTodayData() {
        return isGiftedTodayData;
    }

    public HashMap<String, Boolean> getIsTalkedTodayData() {
        return isTalkedTodayData;
    }

    public ArrayList<String> getDialogues() {
        return dialogues;
    }

    public ArrayList<String> getFavoriteItems() {
        return favoriteItems;
    }

    public String getHomename() {
        return homename;
    }

    public ArrayList<SlotData> getItemsToGive() {
        return itemsToGive;
    }

    public Occupation getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public ArrayList<QuestData> getQuestsdata() {
        return questsdata;
    }

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<Talk> talks) {
        this.talks = talks;
    }

    public Talk getTalkByName(String name) {
        for (Talk talk : this.talks) {
            if(talk.getPlayername().equals(name)) {
                return talk;
            }
        }
        return null;
    }
}
