package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.models.items.CraftingRecipe;
import com.CEliconValley.models.items.craftablemachines.Machine;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

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
    // inventory
    String currentToolName;
    ArrayList<CookingRecipe> cookingRecipes;
    ArrayList<CraftingRecipe> craftingRecipes;
    ArrayList<ArrayList<Integer>> skillLevels;
    ArrayList<MachineData> onGoingMachines;
    boolean isPlayerInVillage;
    // friendship
    // gifts
    // trades
    // buff

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
        this.currentToolName = player.getCurrentTool().getName();
        this.cookingRecipes = new ArrayList<>(player.getCookingRecipes());
        this.craftingRecipes = new ArrayList<>(player.getCraftingRecipes());
        this.skillLevels = new ArrayList<>();
        fillSkillLevels();
        this.onGoingMachines = new ArrayList<>();
        fillOngoingMachinesNames();
        this.isPlayerInVillage = player.isPlayerIsInVillage();
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
