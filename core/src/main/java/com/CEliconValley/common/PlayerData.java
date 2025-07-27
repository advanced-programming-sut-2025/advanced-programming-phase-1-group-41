package com.CEliconValley.common;

import com.CEliconValley.models.Friendship;
import com.CEliconValley.models.Gift;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.Trade;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.models.items.CraftingRecipe;
import com.CEliconValley.models.items.craftablemachines.Machine;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

@Embedded
public class PlayerData {
    @Transient
    private Player player;

    String username;
    double money;
    double savings;
    int farmId;
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

    public PlayerData(Player player) {
        this.player = player;
        this.username = player.getUser().getUsername();
        this.money = player.getMoney();
        this.savings = player.getSavings();
        this.farmId = player.getFarmId();
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

        this.skillLevels = new ArrayList<>();
        fillSkillLevels();
        this.onGoingMachines = new ArrayList<>();
        fillOngoingMachinesNames();
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


    public Player extractPlayer(){
        return null;
    }


    private void fillSkillLevels(){
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

    private void fillOngoingMachinesNames(){
        for (Machine onGoingMachine : player.getOnGoingMachines()) {
            this.onGoingMachines.add(new MachineData(onGoingMachine));
        }
    }


}
