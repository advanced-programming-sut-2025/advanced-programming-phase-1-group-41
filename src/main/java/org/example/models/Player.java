package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import org.example.models.items.*;
import org.example.models.tools.Tool;

import java.util.ArrayList;

@Entity("players")
public class Player {

    public String getChar() {
        return Colors.colorize(0,199,":]");
    }

    @Id
    private ObjectId _id;
    @Transient
    private User user;
    private ObjectId userId;
    private double money;
    private int farmId;
    private int x;
    private int y;
    private double energy;
    private boolean energyUnilimited;
    @Transient
    private Inventory inventory;
    @Transient
    private Tool currentTool;
    private ArrayList<CookingRecipe> recipes;
//    private Farm farm;
//    private ArrayList<Animal> animals;
//    private ArrayList<Skill> skills;
//    private Energy energy;
//    private ArrayList<CraftingRecipe> craftingRecipes;
//    private Buff currentBuff;


//    private ArrayList<Gift> gifts;
//    private ArrayList<Chat> chats;
//    private ArrayList<Trade> trades;
//    private ArrayList<Quest> ongoingQuests;
//    private ArrayList<Quest> finishedQuests;

//    private ArrayList<HashMap<NPC, Integer>> npcs;
    // needs an augmentation
//    private ArrayList<HashMap<Player, FriendshipLevel>> friends;


    public Player() {
    }

    public Player(User user) {
        this.user = user;
        money = 0;
        x = 0;
        y = 0;
        energy = 100;
        this.inventory = new Inventory();
        this.energyUnilimited = false;
        this.currentTool = null;
        this.recipes = new ArrayList<>();
        this.recipes.add(CookingRecipe.BakedFish);
        this.recipes.add(CookingRecipe.Pizza);
        this.recipes.add(CookingRecipe.Spaghetti);
        this.recipes.add(CookingRecipe.Bread);
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
        this.money += money;
    }
    public void decMoney(double delta) {
        this.money -= delta;
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
        App.getGame().incRoundEnergy(delta);
        this.energy -= delta;
    }
    public void incEnergy(double delta) {
        this.energy += delta;
    }


    public boolean isEnergyUnilimited() {
        return energyUnilimited;
    }

    public void setEnergyUnilimited(boolean energyUnilimited) {
        this.energyUnilimited = energyUnilimited;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }


    public ArrayList<CookingRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<CookingRecipe> recipes) {
        this.recipes = recipes;
    }



    @Override
    public String toString() {
        return user.getUsername();
    }
}
