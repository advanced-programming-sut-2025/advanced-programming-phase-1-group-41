package org.example.models.npc.npcCharacters;

import org.example.models.items.CookingRecipe;
import org.example.models.items.Slot;

public class Quest {
    private Slot request;
    private Slot reward;
    private NPC owner;
    private String questName;
    private String questPreTalk;
    private String questPostTalk;
    private Double MoneyPrize;
    private CookingRecipe cookingRecipe;
    private int friendShip;

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
    public Quest(Slot request, Double MoneyPrize, String questName, String questPreTalk, String questPostTalk) {
        this.request = request;
        this.MoneyPrize = MoneyPrize;
        this.owner = owner;
        this.questName = questName;
        this.questPreTalk = questPreTalk;
        this.questPostTalk = questPostTalk;
    }
    public Quest(Slot request, CookingRecipe cookingRecipe, String questName, String questPreTalk, String questPostTalk) {
        this.request = request;
        this.cookingRecipe = cookingRecipe;
        this.owner = owner;
        this.questName = questName;
        this.questPreTalk = questPreTalk;
        this.questPostTalk = questPostTalk;
    }
    public Quest(Slot request,int friendShip, String questName, String questPreTalk, String questPostTalk) {
        this.request = request;
        this.friendShip = friendShip;
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
