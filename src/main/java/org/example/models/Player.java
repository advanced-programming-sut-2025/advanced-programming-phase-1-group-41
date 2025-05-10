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

@Entity
public class Player {
    @Id
    private ObjectId _id;
    private Point point;
    @Reference(lazy = true)
    private User user;
    private int money;
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
//        animals = new ArrayList<>();
//        skills = new ArrayList<>();
        point = new Point(0, 0);
//        skills = new ArrayList<>();
//        energy = new Energy(0);//what is the default value?
        money = 0;
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
    }


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
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

    @Override
    public String toString() {
        return user.getUsername();
    }
}
