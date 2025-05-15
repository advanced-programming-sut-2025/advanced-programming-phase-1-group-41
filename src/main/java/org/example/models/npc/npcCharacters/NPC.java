package org.example.models.npc.npcCharacters;

import org.example.models.App;
import org.example.models.Occupation;
import org.example.models.Player;
import org.example.models.npc.npchomes.NPCHome;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class NPC {


    private String name;
    private Occupation job;
    private NPCHome home;
    private ArrayList<String> dialogues;
    private ArrayList<Item> favorites;
    private ArrayList<Slot> ItemsToGift;
    private ArrayList<Quest> quests;
    private HashMap<Player,Integer> friendShip= new HashMap<>();
    private HashMap<Player,Boolean> isTalkedToday= new HashMap<>();
    private HashMap<Player,Boolean> isGiftedToday = new HashMap<>();
    private int daysToUnlockQ3=0;

    public int getDaysToUnlockQ3() {
        return daysToUnlockQ3;
    }

    private int x=-10;
    private int y=-10;

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public ArrayList<Slot> getItemsToGift() {
        return ItemsToGift;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public ArrayList<String> getDialogues() {
        return dialogues;
    }

    public NPCHome getHome() {
        return home;
    }

    public boolean isTalkedToday(Player player) {
        return isTalkedToday.get(player);
    }

    public void setTalkedToday(Player player,boolean talkedToday) {
        isTalkedToday.put(player,talkedToday) ;
    }

    public boolean isGiftedToday(Player player) {
        return isGiftedToday.get(player);
    }

    public void setGiftedToday(Player player,boolean GiftedToday) {
        isGiftedToday.put(player,GiftedToday);
    }




    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getChar(){
        return "";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDaysToUnlockQ3(int daysToUnlockQ3) {
        this.daysToUnlockQ3 = daysToUnlockQ3;
    }

    public NPC(String name, Occupation job, ArrayList<String> dialogues,
               ArrayList<Item> favorites, ArrayList<Slot> itemsToGift,
               ArrayList<Quest> quests) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.favorites = favorites;
        this.ItemsToGift = itemsToGift;
        this.quests = quests;
        for (Player player :App.getGame().getPlayers()){
            friendShip.put(player,0);
            isTalkedToday.put(player,false);
            isGiftedToday.put(player,false);
            for(int i=0;i<3;i++){
                    quests.get(i).setLocked(player, true);
            }
        }

    }

    public int getFriendShip(Player player) {
        return friendShip.get(player);
    }

    public void incFriendShip(Player player,int friendShip) {
        this.friendShip.put(player,this.friendShip.get(player)+friendShip );
        if(this.friendShip.get(player)>799){
            this.friendShip.put(player,799);
        }
        if(this.friendShip.get(player)>=200){
            this.quests.get(1).setLocked(player,false);
        }
    }

    public String getName() {
        return name;
    }

    public Occupation getJob() {
        return job;
    }

    public String getDialogues(int i) {
        return dialogues.get(i);
    }



}
