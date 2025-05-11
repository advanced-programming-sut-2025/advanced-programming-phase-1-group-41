package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import org.example.models.animals.Animal;
import org.example.models.items.Backpack;
import org.example.models.items.Buff;
import org.example.models.items.CraftingRecipe;
import org.example.models.locations.Farm;
import org.example.models.locations.FarmType;
import org.example.models.npc.NPC;
import org.example.models.npc.Quest;
import org.example.models.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;

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
    private int money;
    private int farmId;
    private int x;
    private int y;
//    private Backpack backpack;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ObjectId get_id() {
        return _id;
    }

    public ObjectId getUserId() {
        return userId;
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

    @Override
    public String toString() {
        return user.getUsername();
    }
}
