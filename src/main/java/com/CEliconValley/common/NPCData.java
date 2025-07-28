package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import dev.morphia.annotations.Embedded;

import java.util.HashMap;

@Embedded
public class NPCData {
    HashMap<String,Integer> friendShipData;
    HashMap<String,Boolean> isTalkedTodayData;
    HashMap<String,Boolean> isGiftedTodayData;
    int daysToUnlockQ3;

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
}
