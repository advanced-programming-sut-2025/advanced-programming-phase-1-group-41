package com.CEliconValley.common;

import com.CEliconValley.models.*;
import com.CEliconValley.models.items.Buff;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.models.items.CraftingRecipe;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.craftablemachines.Machine;
import com.CEliconValley.models.locations.FarmType;
import com.CEliconValley.models.skills.Skill;
import com.CEliconValley.models.tools.LevelTool;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.tools.ToolLevel;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.List;

@Embedded
public class PlayerData {

    String username;
    String avatarPath;
    double money;
    double savings;
    int farmId;
    FarmType farmType;
    int inFarmId;
    int x;
    int y;
    double energy;
    int maxEnergy;
    boolean energyUnlimited;
    int depressionDaysLeft;
    InventoryData inventoryData;
    String currentToolName;
    ArrayList<CookingRecipe> cookingRecipes;
    ArrayList<CraftingRecipe> craftingRecipes;
    ArrayList<ArrayList<Integer>> skillLevels;
    ArrayList<MachineData> onGoingMachines;
    boolean isPlayerInVillage;
    ArrayList<FriendshipData> friendshipsData;
    ArrayList<GiftData> newGiftsData;
    ArrayList<GiftData> receivedGiftsData;
    ArrayList<GiftData> sendGiftsData;
    ArrayList<TradeData> tradesListData;
    ArrayList<TradeData> newTradesListData;
    ArrayList<TradeData> totalTradesListData;
    BuffData buffData;
    ToolLevel toolLevel;
    int questsFinsihed;


    public PlayerData() {
    }

    public PlayerData(Player player) {
        this.username = player.getUser().getUsername();
        this.avatarPath = player.getUser().getAvatarPath();
        this.money = player.getMoney();
        this.savings = player.getSavings();
        this.farmId = player.getFarmId();
        this.farmType = player.getFarmType();
        this.inFarmId = player.getInFarmId();
        this.x = player.getX();
        this.y = player.getY();
        this.energy = player.getEnergy();
        this.maxEnergy = player.getMaxEnergy();
        this.energyUnlimited = player.isEnergyUnlimited();
        this.depressionDaysLeft = player.getDepressionDaysLeft();
        this.currentToolName = player.getCurrentTool() == null ? null : player.getCurrentTool().getName();
        this.cookingRecipes = new ArrayList<>(player.getCookingRecipes());
        this.craftingRecipes = new ArrayList<>(player.getCraftingRecipes());
        this.isPlayerInVillage = player.isPlayerIsInVillage();
        this.questsFinsihed = player.getQuestsFinsihed();
        if(player.getCurrentTool() == null) this.currentToolName = null;
        else{
            if(player.getCurrentTool() instanceof LevelTool lt){
                this.toolLevel = lt.getLevel();
            }
        }
        this.skillLevels = new ArrayList<>();
        fillSkillLevels(player);
        this.onGoingMachines = new ArrayList<>();
        fillOngoingMachinesNames(player);
        this.inventoryData = new InventoryData(player.getInventory());
        this.friendshipsData = new ArrayList<>();
        for (Friendship friendship : player.getFriendships()) {
            this.friendshipsData.add(new FriendshipData(friendship));
        }
        this.newGiftsData = new ArrayList<>();
        this.receivedGiftsData = new ArrayList<>();
        this.sendGiftsData = new ArrayList<>();
        for (Gift newGift : player.getNewGifts()) {
            this.newGiftsData.add(new GiftData(newGift));
        }
        for (Gift receivedGift : player.getReceivedGifts()) {
            this.receivedGiftsData.add(new GiftData(receivedGift));
        }
        for (Gift sendGift : player.getSendGifts()) {
            this.sendGiftsData.add(new GiftData(sendGift));
        }
        this.tradesListData = new ArrayList<>();
        this.newTradesListData = new ArrayList<>();
        this.totalTradesListData = new ArrayList<>();
        for (Trade trade : player.getTradesList()) {
            tradesListData.add(new TradeData(trade));
        }
        for (Trade trade : player.getNewTradesList()) {
            this.newTradesListData.add(new TradeData(trade));
        }
        for (Trade trade : player.getTotalTradesList()) {
            this.totalTradesListData.add(new TradeData(trade));
        }
        this.buffData = player.getBuff() == null ? null : new BuffData(player.getBuff());
    }



    private void fillSkillLevels(Player player){
        skillLevels.add(new ArrayList<>(List.of(
            player.getFarmingSkill().getLevel(), player.getFarmingSkill().getXp()
        )));
        skillLevels.add(new ArrayList<>(List.of(
            player.getMiningSkill().getLevel(), player.getMiningSkill().getXp()
        )));
        skillLevels.add(new ArrayList<>(List.of(
            player.getForagingSkill().getLevel(), player.getForagingSkill().getXp()
        )));
        skillLevels.add(new ArrayList<>(List.of(
            player.getFishingSkill().getLevel(), player.getFishingSkill().getXp()
        )));
    }

    private void fillOngoingMachinesNames(Player player){
        for (Machine onGoingMachine : player.getOnGoingMachines()) {
            this.onGoingMachines.add(new MachineData(onGoingMachine));
        }
    }

    // friendships, gifts, trades
    public Player getPlayer() {
        User user = PlayerFinder.getUserByPlayerName(username);
        Inventory inventory = inventoryData.getInventory();
        Tool tool = currentToolName == null ? null : Finder.getToolByName(currentToolName);
        Buff buff = buffData == null ? null : buffData.getBuff();
        return new Player(cookingRecipes, craftingRecipes, buff, tool, depressionDaysLeft,
                energy, energyUnlimited, farmId, getFarmingSkill(), getFishingSkill(),
                getForagingSkill(), inFarmId, inventory, maxEnergy, getMiningSkill(),
                money, getMachines(), isPlayerInVillage, savings, user, x, y, farmType, questsFinsihed);
    }

    public Skill getFarmingSkill() {
        return new Skill(skillLevels.get(0).get(0), skillLevels.get(0).get(1));
    }
    public Skill getMiningSkill() {
        return new Skill(skillLevels.get(1).get(0), skillLevels.get(1).get(1));
    }
    public Skill getForagingSkill() {
        return new Skill(skillLevels.get(2).get(0), skillLevels.get(2).get(1));
    }
    public Skill getFishingSkill() {
        return new Skill(skillLevels.get(3).get(0), skillLevels.get(3).get(1));
    }

    public ArrayList<Machine> getMachines(){
        ArrayList<Machine> machines = new ArrayList<>();
        if(onGoingMachines == null) return machines;
        for (MachineData onGoingMachine : onGoingMachines) {
            machines.add(onGoingMachine.getMachine());
        }
        return machines;
    }


    public BuffData getBuffData() {
        return buffData;
    }

    public ArrayList<CookingRecipe> getCookingRecipes() {
        return cookingRecipes;
    }

    public ArrayList<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public String getCurrentToolName() {
        return currentToolName;
    }

    public int getDepressionDaysLeft() {
        return depressionDaysLeft;
    }

    public double getEnergy() {
        return energy;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public boolean isEnergyUnlimited() {
        return energyUnlimited;
    }

    public int getFarmId() {
        return farmId;
    }

    public ArrayList<FriendshipData> getFriendshipsData() {
        return friendshipsData;
    }

    public int getInFarmId() {
        return inFarmId;
    }

    public InventoryData getInventoryData() {
        return inventoryData;
    }

    public boolean isPlayerInVillage() {
        return isPlayerInVillage;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public double getMoney() {
        return money;
    }

    public ArrayList<GiftData> getNewGiftsData() {
        return newGiftsData;
    }

    public ArrayList<TradeData> getNewTradesListData() {
        return newTradesListData;
    }

    public ArrayList<MachineData> getOnGoingMachines() {
        return onGoingMachines;
    }

    public ArrayList<GiftData> getReceivedGiftsData() {
        return receivedGiftsData;
    }

    public double getSavings() {
        return savings;
    }

    public ArrayList<GiftData> getSendGiftsData() {
        return sendGiftsData;
    }

    public ArrayList<ArrayList<Integer>> getSkillLevels() {
        return skillLevels;
    }

    public ArrayList<TradeData> getTotalTradesListData() {
        return totalTradesListData;
    }

    public ArrayList<TradeData> getTradesListData() {
        return tradesListData;
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public ToolLevel getToolLevel() {
        return toolLevel;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getQuestsFinsihed() {
        return questsFinsihed;
    }
}
