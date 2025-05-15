package org.example.models.npc;

import org.example.models.items.Slot;

import java.util.ArrayList;

public class Quest {
    private Slot request;
    private Slot reward;
    private NPC owner;
    private String questName;
    private String questPreTalk;
    private String questPostTalk;

    public String getQuestName() {
        return questName;
    }

    public String getQuestPostTalk() {
        return questPostTalk;
    }

    public String getQuestPreTalk() {
        return questPreTalk;
    }

    public Quest(Slot request, Slot reward, String questName, String questPreTalk, String questPostTalk) {
        this.request = request;
        this.reward = reward;
        this.owner = owner;
        this.questName = questName;
        this.questPreTalk = questPreTalk;
        this.questPostTalk = questPostTalk;
    }

    public Slot getRequest() {
        return request;
    }

    public Slot getReward() {
        return reward;
    }

    public NPC getOwner() {
        return owner;
    }
}
