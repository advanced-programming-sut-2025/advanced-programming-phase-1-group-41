package com.CEliconValley.models;

import com.CEliconValley.common.messages.Emotion;
import com.CEliconValley.common.messages.Position;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.items.craftablemachines.Machine;
import com.CEliconValley.models.locations.FarmType;
import com.CEliconValley.models.skills.Skill;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.TerminalColors;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

@Entity("players")
public class Player {

    public String getChar() {
        if(farmId == 1){
            return TerminalColors.colorize(0,199,":]");
        }
        if(farmId == 2){
            return TerminalColors.colorize(0,56,":]");
        }
        if(farmId == 3){
            return TerminalColors.colorize(0,226,":]");
        }
        return TerminalColors.colorize(0,40,":]");
    }

    @Id
    private ObjectId _id;
    @Transient
    private User user;
    private ObjectId userId;
    private double money;
    private double savings;
    private int farmId;
    private int inFarmId;
    private FarmType farmType;
    private int x;
    private int y;
    public int targetx;
    public int targety;
    public float renderx;
    public float rendery;
    public int currentDirection = 3;
    public boolean isMoving = false;
    private double energy;
    private int maxEnergy = 200;
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
    private ArrayList<Gift> newGifts = new ArrayList<>();
    @Transient
    private ArrayList<Gift> receivedGifts = new ArrayList<>();
    @Transient
    private ArrayList<Gift> sendGifts = new ArrayList<>();
    @Transient
    private ArrayList<Trade> tradesList = new ArrayList<>();
    @Transient
    private ArrayList<Trade> newTradesList = new ArrayList<>();
    @Transient
    private ArrayList<Trade> totalTradesList = new ArrayList<>();
    @Transient
    private Buff currentBuff = null;
    int questsFinsihed;

    public ArrayList<Emotion> emotions = new ArrayList<>();
    public int selectedEmotionIndex = 0;

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


    public Player(ArrayList<CookingRecipe> cookingRecipes,
                  ArrayList<CraftingRecipe> craftingRecipes, Buff currentBuff,
                  Tool currentTool, int depressionDaysLeft, double energy,
                  boolean energyUnlimited, int farmId, Skill farmingSkill,
                  Skill fishingSkill, Skill foragingSkill,
                  int inFarmId, Inventory inventory,
                  int maxEnergy, Skill miningSkill, double money,
                  ArrayList<Machine> onGoingMachines,
                  boolean playerIsInVillage,
                  double savings, User user, int x, int y, FarmType farmType, int questsFinsihed,
                  int targetx, int targety, float renderx, float rendery,
                  int currentDirection, boolean isMoving, ArrayList<Emotion> emotions,
                  int selectedEmotionIndex) {
        this.cookingRecipes = cookingRecipes;
        this.craftingRecipes = craftingRecipes;
        this.currentBuff = currentBuff;
        this.currentTool = currentTool;
        this.depressionDaysLeft = depressionDaysLeft;
        this.energy = energy;
        this.energyUnlimited = energyUnlimited;
        this.farmId = farmId;
        this.farmType = farmType;
        this.farmingSkill = farmingSkill;
        this.fishingSkill = fishingSkill;
        this.foragingSkill = foragingSkill;
        this.inFarmId = inFarmId;
        this.inventory = inventory;
        this.maxEnergy = maxEnergy;
        this.miningSkill = miningSkill;
        this.money = money;
        this.onGoingMachines = new ArrayList<>(onGoingMachines);
        this.playerIsInVillage = playerIsInVillage;
        this.savings = savings;
        this.user = user;
        this.x = x;
        this.y = y;
        this.targetx = targetx;
        this.targety = targety;
        this.renderx = renderx;
        this.rendery = rendery;
        this.currentDirection = currentDirection;
        this.isMoving = isMoving;
        this.questsFinsihed = questsFinsihed;
        this.selectedEmotionIndex = selectedEmotionIndex;
        this.emotions = new ArrayList<>(emotions);
    }

    public void handmadePostLoad(ArrayList<Friendship> friendships,
                                 ArrayList<Gift> newGifts, ArrayList<Trade> newTradesList,
                                 ArrayList<Gift> receivedGifts, ArrayList<Gift> sendGifts,
                                 ArrayList<Trade> totalTradesList, ArrayList<Trade> tradesList) {
        this.friendships = new ArrayList<>(friendships);
        this.newGifts = new ArrayList<>(newGifts);
        this.newTradesList = new ArrayList<>(newTradesList);
        this.receivedGifts = new ArrayList<>(receivedGifts);
        this.sendGifts = new ArrayList<>(sendGifts);
        this.totalTradesList = new ArrayList<>(totalTradesList);
        this.tradesList = new ArrayList<>(tradesList);
    }

    public Player() {
    }

    public Player(User user, FarmType farmType, int farmId) {
        this.user = user;
        money = 0;
        savings = 0;
        x = 35;
        y = 55;
        this.targetx = x;
        this.targety = y;
        this.renderx = renderx * 160;
        this.rendery = rendery * 160;
        energy = 200;
        this.farmId = farmId;
        this.farmType = farmType;
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
//        this.craftingRecipes.add(CraftingRecipe.CherryBomb);

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
        this.questsFinsihed = 0;
        this.emotions = new ArrayList<>();
        this.emotions.add(new Emotion(4, true, user.getUsername()));
        this.emotions.add(new Emotion(0, true, user.getUsername()));
        this.emotions.add(new Emotion(1, true, user.getUsername()));
        this.emotions.add(new Emotion(0, false, user.getUsername()));
        this.emotions.add(new Emotion(1, false, user.getUsername()));
        this.selectedEmotionIndex = 0;
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
        double value = 0;
        if(this.friendships == null){
            this.friendships = new ArrayList<>();
        }
        for (Friendship friendship : this.friendships) {
            if(friendship.isAreMarried()){
                if(friendship.getPlayer1().getUser().getUsername().equals(App.getGame().getCurrentPlayer().getUser().getUsername())){
                    value += friendship.getPlayer2().getRealMoney();
                }else{
                    value += friendship.getPlayer1().getRealMoney();
                }
            }
        }
        return money + value;
    }

    public double getRealMoney(){
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

    public void setMaxEnergy(int maxEnergy) {this.maxEnergy = maxEnergy;}

    public void addFriendship(Friendship friendship){friendships.add(friendship);}

    public void addReceivedGift(Gift gift){receivedGifts.add(gift);}

    public void addSendGift(Gift gift){sendGifts.add(gift);}

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
        this.inFarmId = farmId;
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
        if(this.energy > maxEnergy){
            this.energy = maxEnergy;
        }
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

    public ArrayList<Gift> getSendGifts(){return sendGifts;}

    public ArrayList<Gift> getNewGifts(){return newGifts;}

    public ArrayList<Trade> getTradesList(){return tradesList;}

    public ArrayList<Trade> getNewTradesList(){return newTradesList;}

    public ArrayList<Trade> getTotalTradesList(){return totalTradesList;}

    public Buff getBuff() {return currentBuff;}

    public void setBuff(Buff buff) {currentBuff = buff;}

    public boolean hasRecipe(Food food){
//        System.out.println("food is: "+food+" "+food.getRecipe());
        for (CookingRecipe recipe : cookingRecipes) {
//            System.out.println(recipe.toString()+" "+food.getRecipe());
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
        if(depressionDaysLeft > 0){
            depressionDaysLeft--;
            energy = 100;
        }
        money += savings;
        savings = 0;
        maxEnergy = 200;
    }

    public ArrayList<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    @Override
    public String toString() {
        return user.getUsername();
    }


    public void resetEnergy(){
        if(this.energy <= 0){
            System.out.println("since you passed out, you're starting with 150");
            this.energy = (double) (3 * maxEnergy) / 4;
            return;
        }
        this.energy = maxEnergy;
    }

    public void updateHourly(){
        if(currentBuff != null){
            currentBuff.hourPass();
            if(currentBuff.getBuffTime() <= 0){
                currentBuff = null;
                maxEnergy = 200;
                if(energy > maxEnergy){
                    energy = maxEnergy;
                    if(depressionDaysLeft > 0){
                        energy /= 2;
                    }
                }
                return;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(user.getUsername(), player.user.getUsername());
    }

    public int getInFarmId() {
        return inFarmId;
    }

    public void setInFarmId(int inFarmId) {
        this.inFarmId = inFarmId;
    }
    public void resetInFarmId() {
        this.inFarmId = this.farmId;
    }



    public Buff getCurrentBuff() {
        return currentBuff;
    }

    public int getDepressionDaysLeft() {
        return depressionDaysLeft;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public int getQuestsFinsihed() {
        return questsFinsihed;
    }

    public void setQuestsFinsihed(int questsFinsihed) {
        this.questsFinsihed = questsFinsihed;
    }

    public void incrementQuestsFinished(){
        questsFinsihed++;
    }

    public void updatePos(Position pos){
        this.x = pos.x;
        this.y = pos.y;
        this.targetx = pos.targetx;
        this.targety = pos.targety;
        this.renderx = pos.renderx;
        this.rendery = pos.rendery;
        this.currentDirection = pos.currentdirection;
        this.isMoving = pos.ismoving;
    }
}
