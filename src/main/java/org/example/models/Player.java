package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import org.example.models.items.*;
import org.example.models.items.craftablemachines.Machine;
import org.example.models.skills.Skill;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

@Entity("players")
public class Player {

    public String getChar() {
        if(farmId == 1){
            return Colors.colorize(0,199,":]");
        }
        if(farmId == 2){
            return Colors.colorize(0,56,":]");
        }
        if(farmId == 3){
            return Colors.colorize(0,226,":]");
        }
        return Colors.colorize(0,40,":]");
    }

    @Id
    private ObjectId _id;
    @Transient
    private User user;
    private ObjectId userId;
    private double money;
    private double savings;
    private int farmId;
    private int x;
    private int y;
    private double energy;
    private boolean energyUnlimited;
    private int depressionDaysLeft = 0;
    @Transient
    private Inventory inventory;
    @Transient
    private Tool currentTool;
    private ArrayList<CookingRecipe> cookingRecipes;
    private ArrayList<CraftingRecipe> craftingRecipes;
    @Transient
    private Skill farmingSkill;
    @Transient
    private Skill miningSkill;
    @Transient
    private Skill foragingSkill;
    @Transient
    private Skill fishingSkill;
    @Transient
    private ArrayList<Machine> onGoingMachines;
    private boolean playerIsInVillage;
    @Transient
    private ArrayList<Friendship> friendships;
    @Transient
    private final ArrayList<Gift> newGifts = new ArrayList<>();
    @Transient
    private final ArrayList<Gift> receivedGifts = new ArrayList<>();
    @Transient
    private final ArrayList<Trade> tradesList = new ArrayList<>();
    @Transient
    private final ArrayList<Trade> newTradesList = new ArrayList<>();
    @Transient
    private final ArrayList<Trade> totalTradesList = new ArrayList<>();
//    private Farm farm;
//    private ArrayList<Animal> animals;
//    private ArrayList<Skill> skills;
//    private Energy energy;
//    private ArrayList<CraftingRecipe> craftingRecipes;
//    private Buff currentBuff;


//    private ArrayList<Gift> gifts;
//    private ArrayList<Chat> chats;
//    private ArrayList<Quest> ongoingQuests;
//    private ArrayList<Quest> finishedQuests;

//    private ArrayList<HashMap<NPC, Integer>> npcs;
    // needs an augmentation
    private ArrayList<HashMap<Player, FriendshipLevel>> friends;


    public Player() {
    }

    public Player(User user) {
        this.user = user;
        money = 0;
        savings = 0;
        x = 0;
        y = 0;
        energy = 100;
        this.inventory = new Inventory();
        this.energyUnlimited = false;
        this.currentTool = null;
        this.cookingRecipes = new ArrayList<>();
        this.cookingRecipes.add(CookingRecipe.FriedEgg);
        this.cookingRecipes.add(CookingRecipe.BakedFish);
        this.cookingRecipes.add(CookingRecipe.Salad);
        this.craftingRecipes = new ArrayList<>();
        this.craftingRecipes.add(CraftingRecipe.Furnace);
        this.craftingRecipes.add(CraftingRecipe.ScareCrow);
        this.craftingRecipes.add(CraftingRecipe.MayonnaiseMachine);

        this.friendships = new ArrayList<>();

        this.farmingSkill = new Skill();
        this.miningSkill = new Skill();
        this.foragingSkill = new Skill();
        this.fishingSkill = new Skill();

        this.onGoingMachines = new ArrayList<>();
//        animals = new ArrayList<>();
//        skills = new ArrayList<>();
//        skills = new ArrayList<>();
//        energy = new Energy(0);//what is the default value?
//        craftingRecipes = new ArrayList<>();
//        gifts = new ArrayList<>();
//        chats = new ArrayList<>();
//        trades = new ArrayList<>();
//        ongoingQuests = new ArrayList<>();
//        finishedQuests = new ArrayList<>();
//        npcs = new ArrayList<>();
//        friends = new ArrayList<>();
//        backpack = Backpack.Default;
//        this.farm = null;
        _id = new ObjectId();
    }

    public void prepareForSaving() {
        if (user != null) {
            System.out.println("Debug: Setting userId : " + user.get_id());
            userId = user.get_id();
        } else {
            System.out.println("Warning: currentUser is null");
        }
    }


    public boolean isPlayerIsInVillage() {
        return playerIsInVillage;
    }

    public void setPlayerIsInVillage(boolean playerIsInVillage) {
        this.playerIsInVillage = playerIsInVillage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    public void incMoney(double delta) {
        this.money += delta;
    }
    public void decMoney(double delta) {
        this.money -= delta;
    }

    public double getSavings() {
        return savings;
    }
    public void incSavings(double delta) {
        this.savings += delta;
    }
    public void decSavings(double delta) {
        this.savings -= delta;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public ObjectId get_id() {
        return _id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addFriendship(Friendship friendship){friendships.add(friendship);}

    public void addReceivedGift(Gift gift){receivedGifts.add(gift);}

    public void addNewGift(Gift gift){newGifts.add(gift);}

    public void removeNewGift(Gift gift){newGifts.remove(gift);}

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void decEnergy(double delta) {
        if(this.energyUnlimited){
            System.out.println("since your energy is set to unlimited, no decrease of energy for u");
            return;
        }
        App.getGame().incRoundEnergy(delta);
        this.energy -= delta;
    }
    public void decEnergyTool(double delta) {
        if(this.energyUnlimited){
            System.out.println("since your energy is set to unlimited, no decrease of energy for u");
            return;
        }
        delta *= App.getGame().getWeatherType().getEnergy();
        App.getGame().incRoundEnergy(delta);
        this.energy -= delta;
    }
    public void incEnergy(double delta) {
        this.energy += delta;
    }


    public boolean isEnergyUnlimited() {
        return energyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        this.energy = 10000;
        this.energyUnlimited = energyUnlimited;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }


    public ArrayList<CookingRecipe> getCookingRecipes() {
        return cookingRecipes;
    }

    public void setCookingRecipes(ArrayList<CookingRecipe> cookingRecipes) {
        this.cookingRecipes = cookingRecipes;
    }

    public Skill getFarmingSkill() {
        return farmingSkill;
    }

    public Skill getMiningSkill() {
        return miningSkill;
    }

    public Skill getForagingSkill() {
        return foragingSkill;
    }

    public Skill getFishingSkill() {
        return fishingSkill;
    }

    public void setDepressionDaysLeft(int depressionDaysLeft) {
        this.depressionDaysLeft = depressionDaysLeft;
    }

    public ArrayList<Machine> getOnGoingMachines() {
        return onGoingMachines;
    }

    public ArrayList<Friendship> getFriendships() {return friendships;}

    public ArrayList<Gift> getReceivedGifts(){return receivedGifts;}

    public ArrayList<Gift> getNewGifts(){return newGifts;}

    public ArrayList<Trade> getTradesList(){return tradesList;}

    public ArrayList<Trade> getNewTradesList(){return newTradesList;}

    public ArrayList<Trade> getTotalTradesList(){return totalTradesList;}

    public boolean hasRecipe(Food food){
        System.out.println("food is: "+food+" "+food.getRecipe());
        for (CookingRecipe recipe : cookingRecipes) {
            System.out.println(recipe.toString()+" "+food.getRecipe());
            if(recipe.equals(food.getRecipe())){
                return true;
            }
        }
        return false;
    }
    public Friendship findFriendship(Player player){
        for (Friendship friendship : friendships) {
            if(friendship.getPlayer1() == player || friendship.getPlayer2() == player){
                return friendship;
            }
        }
        return null;
    }

    public void dailyUpdates(){
        //TODO Half Energy
        if(depressionDaysLeft > 0){
            depressionDaysLeft--;
            energy = energy / 2;
        }
        money += savings;
        savings = 0;
    }

    public ArrayList<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    @Override
    public String toString() {
        return user.getUsername();
    }


}
