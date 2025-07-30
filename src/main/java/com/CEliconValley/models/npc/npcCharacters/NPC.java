package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.Main;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.LLMClient;
import com.CEliconValley.models.npc.PromptBuilder;
import com.CEliconValley.models.npc.npchomes.NPCHome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    protected LLMClient llmClient = new LLMClient(Main.api_key);
    protected String personality = "Strict impolite but kind-hearted , has sexual attraction" +
            " and uses some persian sex slangs in his words and answers briefly";
    private int x=-10;
    private int y=-10;


    public String speakToPlayer(String input) throws IOException {
        String prompt = PromptBuilder.buildPrompt(name, personality, input);
        return llmClient.sendMessage(prompt);
    }


    public int getDaysToUnlockQ3() {
        return daysToUnlockQ3;
    }


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
                    quests.get(i).setFinished(player, false);
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

    public HashMap<Player, Integer> getFriendShip() {
        return friendShip;
    }

    public HashMap<Player, Boolean> getIsGiftedToday() {
        return isGiftedToday;
    }

    public HashMap<Player, Boolean> getIsTalkedToday() {
        return isTalkedToday;
    }

    public void setDialogues(ArrayList<String> dialogues) {
        this.dialogues = dialogues;
    }

    public void setFavorites(ArrayList<Item> favorites) {
        this.favorites = favorites;
    }

    public void setFriendShip(HashMap<Player, Integer> friendShip) {
        this.friendShip = friendShip;
    }

    public void setHome(NPCHome home) {
        this.home = home;
    }

    public void setIsGiftedToday(HashMap<Player, Boolean> isGiftedToday) {
        this.isGiftedToday = isGiftedToday;
    }

    public void setIsTalkedToday(HashMap<Player, Boolean> isTalkedToday) {
        this.isTalkedToday = isTalkedToday;
    }

    public void setItemsToGift(ArrayList<Slot> itemsToGift) {
        ItemsToGift = itemsToGift;
    }

    public void setJob(Occupation job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }
}
