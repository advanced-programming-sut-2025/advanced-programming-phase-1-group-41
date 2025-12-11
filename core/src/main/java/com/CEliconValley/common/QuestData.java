package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.CEliconValley.models.npc.npcCharacters.Quest;
import dev.morphia.annotations.Embedded;

import java.util.HashMap;
@Embedded
public class QuestData {
    private SlotData requestdata;
    private SlotData rewarddata;
    private String ownername;
    private String questName;
    private String questPreTalk;
    private String questPostTalk;
    private Double MoneyPrize=0.0;
    private CookingRecipe cookingRecipe=null;
    private HashMap<String,Boolean> isLocked=new HashMap<>();
    private HashMap<String,Boolean> isFinished=new HashMap<>();

    public QuestData(){}
    public QuestData(Quest quest) {
        requestdata =  new SlotData(quest.getRequest());
        rewarddata = quest.getReward() == null ? null : new SlotData(quest.getReward());
        questName = quest.getQuestName();
        this.ownername = quest.getOwner().getName();
        questPreTalk = quest.getQuestPreTalk();
        questPostTalk = quest.getQuestPostTalk();
        MoneyPrize = quest.getMoneyPrize();
        cookingRecipe = quest.getCookingRecipe();
        quest.getIsLocked().forEach((key, value) -> {
            isLocked.put(key.getUser().getUsername(), value);
        });
        quest.getIsFinished().forEach((key, value) -> {
            isFinished.put(key.getUser().getUsername(), value);
        });
    }

    public CookingRecipe getCookingRecipe() {
        return cookingRecipe;
    }

    public HashMap<String, Boolean> getIsFinished() {
        return isFinished;
    }

    public HashMap<String, Boolean> getIsLocked() {
        return isLocked;
    }

    public Double getMoneyPrize() {
        return MoneyPrize;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getQuestName() {
        return questName;
    }

    public String getQuestPostTalk() {
        return questPostTalk;
    }

    public String getQuestPreTalk() {
        return questPreTalk;
    }

    public SlotData getRequestdata() {
        return requestdata;
    }

    public SlotData getRewarddata() {
        return rewarddata;
    }
}
