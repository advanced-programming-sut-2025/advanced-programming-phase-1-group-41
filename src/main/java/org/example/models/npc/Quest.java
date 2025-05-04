package org.example.models.npc;

import org.example.models.items.Slot;

import java.util.ArrayList;

public class Quest {
    private Slot request;
    private Slot reward;
    private NPC owner;

    public Quest(Slot request, Slot reward, NPC owner) {
        this.request = request;
        this.reward = reward;
        this.owner = owner;
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
