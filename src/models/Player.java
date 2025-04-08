package models;

import models.animals.Animal;
import models.items.Backpack;
import models.items.Buff;
import models.items.CraftingRecipe;
import models.locations.Farm;
import models.npc.NPC;
import models.npc.Quest;
import models.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    Point point;
    Farm farm;
    ArrayList<Animal> animals;
    ArrayList<Skill> skills;
    Energy energy;
    User user;
    Backpack backpack;
    int money;
    ArrayList<CraftingRecipe> craftingRecipes;
    Buff currentBuff;


    ArrayList<Gift> gifts;
    ArrayList<Chat> chats;
    ArrayList<Trade> trades;
    ArrayList<Quest> ongoingQuests;
    ArrayList<Quest> finishedQuests;

    ArrayList<HashMap<NPC, Integer>> npcs;
    // needs an augmentation
    ArrayList<HashMap<Player, FriendshipLevel>> friends;
}
