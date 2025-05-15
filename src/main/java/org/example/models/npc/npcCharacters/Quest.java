package org.example.models.npc.npcCharacters;

import org.example.models.App;
import org.example.models.Player;
import org.example.models.items.CookingRecipe;
import org.example.models.items.Slot;

import java.util.HashMap;

public class Quest {
    private Slot request;
    private Slot reward;
    private NPC owner;
    private String questName;
    private String questPreTalk;
    private String questPostTalk;
    private Double MoneyPrize=0.0;
    private CookingRecipe cookingRecipe=null;
    private HashMap<Player,Boolean> isLocked=new HashMap<>();
    private HashMap<Player,Boolean> isFinished=new HashMap<>();


    public boolean isLocked(Player player) {
        return isLocked.get(player);
    }

    public boolean isFinished(Player player) {
        return isFinished.get(player);
    }

    public void setLocked(Player player, boolean locked) {
        this.isLocked.put(player, locked);
    }

    public void setFinished(Player player, boolean finished) {
        this.isFinished.put(player, finished);
    }



    public void setRequest(Slot request) {
        this.request = request;
    }

    public void setQuestPreTalk(String questPreTalk) {
        this.questPreTalk = questPreTalk;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public void setOwner(NPC owner) {
        this.owner = owner;
    }


    public int getFriendShip() {
        return friendShip;
    }


    public Double getMoneyPrize() {
        return MoneyPrize;
    }


    public CookingRecipe getCookingRecipe() {
        return cookingRecipe;
    }


    private int friendShip=0;

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

    public Quest(Slot request, int friendShip, String questName, String questPreTalk, String questPostTalk) {
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
