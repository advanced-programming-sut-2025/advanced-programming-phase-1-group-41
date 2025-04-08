package models.npc;

import models.Occupation;
import models.buildings.marketplaces.Marketplace;
import models.buildings.npchomes.NPCHome;
import models.items.Item;
import models.items.Slot;

import java.util.ArrayList;

public enum NPC {
    Sebastien,
    Abigail,
    Harvey,
    Lia,
    Robin
    ;

    String name;
    Occupation job;
    NPCHome home;
    Marketplace marketplace;
    String dialogues;
    ArrayList<Item> favorites;
    ArrayList<Slot> ItemsToGift;

    Quest firstQuest;
    Quest secondQuest;
    Quest thirdQuest;
}
