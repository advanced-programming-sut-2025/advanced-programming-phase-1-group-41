package org.example.models;

import org.example.models.animals.Animal;
import org.example.models.items.Backpack;
import org.example.models.items.Buff;
import org.example.models.items.CraftingRecipe;
import org.example.models.locations.Farm;
import org.example.models.npc.NPC;
import org.example.models.npc.Quest;
import org.example.models.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private Point point;
    private Farm farm;
    private ArrayList<Animal> animals;
    private ArrayList<Skill> skills;
    private Energy energy;
    private User user;
    private Backpack backpack;
    private int money;
    private ArrayList<CraftingRecipe> craftingRecipes;
    private Buff currentBuff;


    private ArrayList<Gift> gifts;
    private ArrayList<Chat> chats;
    private ArrayList<Trade> trades;
    private ArrayList<Quest> ongoingQuests;
    private ArrayList<Quest> finishedQuests;

    private ArrayList<HashMap<NPC, Integer>> npcs;
    // needs an augmentation
    private ArrayList<HashMap<Player, FriendshipLevel>> friends;

    public Player(Point point, Farm farm, ArrayList<Animal> animals, ArrayList<Skill> skills, Energy energy, User user, Backpack backpack, int money, ArrayList<CraftingRecipe> craftingRecipes, Buff currentBuff, ArrayList<Gift> gifts, ArrayList<Chat> chats, ArrayList<Trade> trades, ArrayList<Quest> ongoingQuests, ArrayList<Quest> finishedQuests, ArrayList<HashMap<NPC, Integer>> npcs, ArrayList<HashMap<Player, FriendshipLevel>> friends) {
        this.point = point;
        this.farm = farm;
        this.animals = animals;
        this.skills = skills;
        this.energy = energy;
        this.user = user;
        this.backpack = backpack;
        this.money = money;
        this.craftingRecipes = craftingRecipes;
        this.currentBuff = currentBuff;
        this.gifts = gifts;
        this.chats = chats;
        this.trades = trades;
        this.ongoingQuests = ongoingQuests;
        this.finishedQuests = finishedQuests;
        this.npcs = npcs;
        this.friends = friends;
    }

    public Point getPoint() {
        return point;
    }

    public Farm getFarm() {
        return farm;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public Energy getEnergy() {
        return energy;
    }

    public User getUser() {
        return user;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public Buff getCurrentBuff() {
        return currentBuff;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public ArrayList<Quest> getOngoingQuests() {
        return ongoingQuests;
    }

    public ArrayList<Quest> getFinishedQuests() {
        return finishedQuests;
    }

    public ArrayList<HashMap<NPC, Integer>> getNpcs() {
        return npcs;
    }

    public ArrayList<HashMap<Player, FriendshipLevel>> getFriends() {
        return friends;
    }
}
