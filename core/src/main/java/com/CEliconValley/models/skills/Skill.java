package com.CEliconValley.models.skills;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.models.items.CraftingRecipe;

public class Skill {
    private int level;
    private int xp;
    public Skill() {
        this.level = 0;
        this.xp = 0;
    }

    public int getLevel() {
        return level;
    }

    public boolean isMaxLevel(){
        if(level >= 4){
            return true;
        }
        return false;
    }

    public void increaseLevel() {
        this.level = Math.min(this.level + 1, 4);
    }

    public int getXp() {
        return xp;
    }

    public void increaseXp(int delta) {
        this.xp += delta;
        if(this.xp > 50 + 100 * level){
            this.xp %= 50 + 100 * level;
            increaseLevel();
            updateSkillOnPlayer(this);
        }
    }

    private static void updateSkillOnPlayer(Skill skill){
        Player player = App.getGame().getCurrentPlayer();
        if(player.getFarmingSkill() == skill){
            switch(skill.getLevel()){
                case 1 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.Sprinkler);
                    System.out.println("    you got "+CraftingRecipe.Sprinkler.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.BeeHouse);
                    System.out.println("    you got "+CraftingRecipe.BeeHouse.getName());
                    player.getCookingRecipes().add(CookingRecipe.FarmerLunch);
                    System.out.println("    you got "+CookingRecipe.FarmerLunch.getName());
                }
                case 2 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.QualitySprinkler);
                    System.out.println("    you got "+CraftingRecipe.QualitySprinkler.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.ScareCrow);
                    System.out.println("    you got "+CraftingRecipe.ScareCrow.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.CheesePress);
                    System.out.println("    you got "+CraftingRecipe.CheesePress.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.PreservesJar);
                    System.out.println("    you got "+CraftingRecipe.PreservesJar.getName());
                }
                case 3 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.IridiumSprinkler);
                    System.out.println("    you got "+CraftingRecipe.IridiumSprinkler.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.Keg);
                    System.out.println("    you got "+CraftingRecipe.Keg.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.Loom);
                    System.out.println("    you got "+CraftingRecipe.Loom.getName());
                    player.getCraftingRecipes().add(CraftingRecipe.OilMaker);
                    System.out.println("    you got "+CraftingRecipe.OilMaker.getName());
                }
            }
        }
        else if(player.getMiningSkill() == skill){
            switch(skill.getLevel()){
                case 1 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.CherryBomb);
                    System.out.println("    you got "+CraftingRecipe.CherryBomb.getName());
                    player.getCookingRecipes().add(CookingRecipe.MinerTreat);
                    System.out.println("    you got "+CookingRecipe.MinerTreat.getName());
                }
                case 2 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.Bomb);
                    System.out.println("    you got "+CraftingRecipe.Bomb.getName());
                }
                case 3 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.MegaBomb);
                    System.out.println("    you got "+CraftingRecipe.MegaBomb.getName());
                }
            }
        }
        else if(player.getForagingSkill() == skill){
            switch (skill.getLevel()){
                case 1 -> {}
                case 2 -> {
                    player.getCookingRecipes().add(CookingRecipe.VegetableMedly);
                    System.out.println("    you got "+CookingRecipe.VegetableMedly.getName());
                }
                case 3 -> {
                    player.getCookingRecipes().add(CookingRecipe.SurvivalBurger);
                    System.out.println("    you got "+CookingRecipe.SurvivalBurger.getName());
                }
                case 4 -> {
                    player.getCraftingRecipes().add(CraftingRecipe.MysticTreeSeed);
                    System.out.println("    you got "+CraftingRecipe.MysticTreeSeed.getName());
                }
            }
        }
        else if(player.getFishingSkill() == skill){
            switch (skill.getLevel()){
                case 1 -> {

                }
                case 2 -> {
                    player.getCookingRecipes().add(CookingRecipe.DishOSea);
                    System.out.println("    you got "+CookingRecipe.DishOSea.getName());

                }
                case 3 -> {
                    player.getCookingRecipes().add(CookingRecipe.SeaformPuddin);
                    System.out.println("    you got "+CookingRecipe.SeaformPuddin.getName());
                }
                case 4 -> {

                }
            }
        }
    }
}
