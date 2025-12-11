package com.CEliconValley.models.npc.npcCharacters;

import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.common.Talk;
import com.CEliconValley.common.messages.Messagenpc;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.npc.LLMClient;
import com.CEliconValley.models.npc.PromptBuilder;
import com.CEliconValley.models.npc.npchomes.NPCHome;

import java.io.IOException;
import java.util.*;

public abstract class NPC {


    private String name;
    private Occupation job;
    private NPCHome home;
    private ArrayList<String> dialogues;
    private ArrayList<Talk> talks;
    private ArrayList<Item> favorites;
    private ArrayList<Slot> ItemsToGift;
    private ArrayList<Quest> quests;
    private HashMap<Player, Integer> friendShip = new HashMap<>();
    private HashMap<Player, Boolean> isTalkedToday = new HashMap<>();
    private HashMap<Player, Boolean> isGiftedToday = new HashMap<>();
    private int daysToUnlockQ3 = 0;
    public int x = -10;
    public int y = -10;
    public boolean isOutside = false;
    public int randomX = -1;
    public int randomY = -1;
    public int targetX = -1;
    public int targetY = -1;
    public float renderX = -1;
    public float renderY = -1;
    public boolean randomSetter = false;
    public boolean isMoving = false;
    public Queue<Node> movementQueue = new LinkedList<>();
    public int currentDirection = 3;
    public boolean isSet = false;
    public boolean shouldGoHome = false;
    public boolean shouldGoToWork = false;
    private long lastLLMRequestTime = 0;
    private static final long MIN_REQUEST_INTERVAL_MS = 3000; // 3 seconds

    public boolean canSendLLMRequest() {
        long now = System.currentTimeMillis();
        if (now - lastLLMRequestTime < MIN_REQUEST_INTERVAL_MS) return false;
        lastLLMRequestTime = now;
        return true;
    }

    protected LLMClient llmClient = new LLMClient(App.backup);
    protected String personality = "Strict impolite but kind-hearted , has sexual attraction" +
        " and uses some new slangs in his words and answers briefly";

    public static final int CELL_SIZE = (int)  160;


    public String speakToPlayer(String input, WeatherType weatherType, Season season) throws IOException {
        String prompt = PromptBuilder.buildPrompt(name, personality, weatherType, season, input);
        return llmClient.sendMessageSync(prompt);
    }

    public boolean reachedDestination() {
        return x == randomX && y == randomY;
    }

    public void setRandomPoint() {

        if (!isSet && x >= 0) {
            randomX = x;
            randomY = y;
            targetX = x;
            targetY = y;
            renderX = x * CELL_SIZE;
            renderY = y * CELL_SIZE;
            isSet = true;
            return;
        }

        if (isOutside) {
            Random random = new Random();
            Village village = App.getGame().getVillage();
            while (true) {
                int randomIndex = random.nextInt(village.getCells().size());
                Cell cell = village.getCells().get(randomIndex);
                int dx = cell.getX() - x;
                int dy = cell.getY() - y;
                dx = Math.abs(dx);
                dy = Math.abs(dy);
                if (dx + dy > 10) {
                    continue;
                }
                if (village.canMoveTo(cell.getX(), cell.getY())) {
                    this.randomX = cell.getX();
                    this.randomY = cell.getY();
                    break;
                }
            }
        }else{
            int hour = App.getGame().getTime().getHour();
            if(getJob() != null && getJob()!=Occupation.Jobless && (Marketplace.goToWork <= hour && hour <= Marketplace.outOfWork)){
                Building building = Finder.getBuildingBynpc(this, true);
                if(building == null){
                    System.out.println("got a null marketplace "+name);
                    return;
                }
                Cell cell = App.getGame().getVillage().getCellAroundABuilding(building);
                this.randomX = cell.getX();
                this.randomY = cell.getY();
            }else if(getHome() != null &&(hour <= Marketplace.outOfHome || hour >= Marketplace.goToHome)){
                Building building = Finder.getBuildingBynpc(this, false);
                if(building == null){
                    System.out.println("got a null");
                    return;
                }
                Cell cell = App.getGame().getVillage().getCellAroundABuilding(building);
                this.randomX = cell.getX();
                this.randomY = cell.getY();
            }
        }
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

    public void setTalkedToday(Player player, boolean talkedToday) {
        isTalkedToday.put(player, talkedToday);
    }

    public boolean isGiftedToday(Player player) {
        return isGiftedToday.get(player);
    }

    public void setGiftedToday(Player player, boolean GiftedToday) {
        isGiftedToday.put(player, GiftedToday);
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getChar() {
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
               ArrayList<Item> favorites, ArrayList<Slot> itemsToGift) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.favorites = favorites;
        this.ItemsToGift = itemsToGift;
        this.talks = new ArrayList<>();
        for (Player player : App.getGame().getPlayers()) {
            this.talks.add(new Talk(player.getUser().getUsername()));
        }
    }

    public void postLoad(ArrayList<Quest> quests) {
        this.quests = quests;
        for (Quest quest : this.quests) {
            quest.setOwner(this);
        }
        for (Player player : App.getGame().getPlayers()) {
            friendShip.put(player, 0);
            isTalkedToday.put(player, false);
            isGiftedToday.put(player, false);
            for (int i = 0; i < 3; i++) {
                if(i != 0){
                    this.quests.get(i).setLocked(player, true);
                }
                this.quests.get(i).setFinished(player, false);
            }
            this.quests.get(0).setLocked(player, false);
        }
    }

    public int getFriendShip(Player player) {
        return friendShip.get(player);
    }

    public void incFriendShip(Player player, int friendShip) {
        this.friendShip.put(player, this.friendShip.get(player) + friendShip);
        if (this.friendShip.get(player) > 799) {
            this.friendShip.put(player, 799);
        }
        if (this.friendShip.get(player) >= 200) {
            this.quests.get(1).setLocked(player, false);
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

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public int getRandomX() {
        return randomX;
    }

    public void setRandomX(int randomX) {
        this.randomX = randomX;
    }

    public int getRandomY() {
        return randomY;
    }

    public void setRandomY(int randomY) {
        this.randomY = randomY;
    }

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<Talk> talks) {
        this.talks = talks;
    }

    public Talk getTalkByName(String name) {
        for (Talk talk : this.talks) {
            if(talk.getPlayername().equals(name)) {
                return talk;
            }
        }
        return null;
    }

    public LLMClient getLlmClient() {
        return llmClient;
    }

    public Queue<Node> getMovementQueue() {
        return movementQueue;
    }

    public String getPersonality() {
        return personality;
    }
}
