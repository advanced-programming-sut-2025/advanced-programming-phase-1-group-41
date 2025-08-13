package com.CEliconValley.controllers.subgames;

import com.CEliconValley.common.messages.Messagenpc;
import com.CEliconValley.models.*;

import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.CEliconValley.models.npc.npcCharacters.Quest;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.TerminalColors;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

import static com.CEliconValley.models.Finder.parseItem;
import static com.CEliconValley.models.Finder.parseNPC;

public class NPCController {

    public Result meetNpc(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        String npcName = matcher.group(1);
        String input = matcher.group(2);

        NPC npc = App.getGame().getVillage().getNPCs().stream()
            .filter(n -> n.getName().equals(npcName))
            .findFirst()
            .orElse(null);

        if (npc == null) {
            return new Result(false, "No one’s here with that name or they're not around.");
        }
//
//        if (!npc.isTalkedToday(player)) {
//            npc.incFriendShip(player, 20);
//            npc.setTalkedToday(player, true);
//            npc.getQuests().get(0).setLocked(player, false);
//        }
//
//        npc.getTalkByName(playername).getTalks().add(new Messagenpc(false, input));

        // Delegate async LLM response handling
        npc.getLlmClient().meetNpcAsync(matcher, playername);

        return new Result(true, npc.getName() + " is thinking and will respond shortly.");
    }

    public Result giftToNpc(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Inventory inventory = player.getInventory();
        String npcName = matcher.group(1);
        String itemName = matcher.group(2);
        NPC npc = null;
        for (NPC npc1 : App.getGame().getVillage().getNPCs()) {
            if (npc1.getName().equals(npcName)) {
                npc = npc1;
            }
        }
//        NPC npc=findNPCAround(npcName, player);
        if (npc == null) {
            if (!getNPCList().contains(npcName)) {
                return new Result(false, "no one`s here with that name");
            }
            return new Result(false, npcName + " is not around");
        }
        Item item = parseItem(itemName);
        if (item == null) {
            return new Result(false, "no item with that name");
        }
        if (inventory.getSlotByItem(item) == null) {
            return new Result(false, "you dont have " + itemName);
        }
        if ((item instanceof Tool || item instanceof CraftableMachine)) {
            return new Result(false, "tools or Craftable machines can not be gifted");
        }
        inventory.removeFromInventory(item, 1);
        if (npc.getFavorites().contains(item)) {
            if (!npc.isGiftedToday(player)) {
                npc.incFriendShip(player, 200);
            }
            npc.setGiftedToday(player, true);
            return new Result(true, npc.getDialogues(10));
        } else {
            if (!npc.isGiftedToday(player)) {
                npc.incFriendShip(player, 50);
            }
            npc.setGiftedToday(player, true);
            return new Result(true, npc.getDialogues(9));
        }
    }

    public Result friendshipList(Matcher matcher) {
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Player player = App.getGame().getCurrentPlayer();
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            String levelOfFriendship = "";
            int levelOfFriendshipNumber = -1;
            if (npc.getFriendShip(player) < 200) {
                levelOfFriendship = "knows";
                levelOfFriendshipNumber = 0;
            } else if (npc.getFriendShip(player) < 400) {
                levelOfFriendship = "likes";
                levelOfFriendshipNumber = 1;
            } else if (npc.getFriendShip(player) < 600) {
                levelOfFriendship = "admires";
                levelOfFriendshipNumber = 2;
            } else if (npc.getFriendShip(player) < 800) {
                levelOfFriendship = "loves";
                levelOfFriendshipNumber = 3;
            }
            System.out.println(npc.getName() + " " + levelOfFriendship + " you (friendShip Level : " + levelOfFriendshipNumber + " [" + npc.getFriendShip(player) + "] )");
        }
        return new Result(true, " ");
    }

    public Result questList(Matcher matcher) {
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Player player = App.getGame().getCurrentPlayer();
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            int i = 1;
            System.out.println(TerminalColors.foreColor(15) + npc.getName() + "`s quests :" + TerminalColors.RESET);
            for (Quest quest : npc.getQuests()) {
                if (quest.isLocked(player)) {
                    System.out.println(TerminalColors.foreColor(124) + i + "-quest " + quest.getQuestName() + " is locked" + TerminalColors.RESET);
                } else if (quest.isFinished(player)) {
                    System.out.println(i + "-quest " + quest.getQuestName() + " is finished");
                } else {
                    System.out.printf(TerminalColors.foreColor(46) + i + "-quest " + quest.getQuestName() + " -> " + quest.getQuestPreTalk() + " | objective : deliver " + quest.getRequest().getQuantity() + " " + quest.getRequest().getItem().getName() + " to " + npc.getName() + TerminalColors.RESET);
                    if (quest.getMoneyPrize() != 0.0) {
                        System.out.printf(TerminalColors.foreColor(46) + " | reward : " + quest.getMoneyPrize() + " gold\n" + TerminalColors.RESET);
                    } else if (quest.getFriendShip() != 0) {
                        System.out.printf(TerminalColors.foreColor(46) + " | reward : " + quest.getFriendShip() + " points of friendship\n" + TerminalColors.RESET);
                    } else if (quest.getCookingRecipe() != null) {
                        System.out.printf(TerminalColors.foreColor(46) + " | reward : " + quest.getCookingRecipe().getName() + "\n" + TerminalColors.RESET);
                    } else {
                        System.out.printf(TerminalColors.foreColor(46) + " | reward : " + quest.getReward().getQuantity() + " " + quest.getReward().getItem().getName() + "\n" + TerminalColors.RESET);
                    }
                }
                i++;
            }
        }
        return new Result(true, " ");
    }

    public Result finishQuest(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Inventory inventory = player.getInventory();
        int indexOfQuest = Integer.parseInt(matcher.group(1));
        String npcName = matcher.group(2).trim();
        System.out.println(indexOfQuest+" "+npcName);
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            if (!npc.getName().equals(npcName)) continue;
            Quest quest = npc.getQuests().get(indexOfQuest - 1);
            if (quest.isLocked(player)) {
                if (indexOfQuest == 1) {
                    return new Result(false, "quest " + quest.getQuestName() + " is locked, talk to " + npc.getName() + " to unlock it");
                } else if (indexOfQuest == 2) {
                    return new Result(false, "quest " + quest.getQuestName() + " is locked, befriend with " + npc.getName() + " to unlock it");
                } else if (indexOfQuest == 3) {
                    return new Result(false, "quest " + quest.getQuestName() + " is locked, wait " + npc.getDaysToUnlockQ3() + " days to unlock it");
                }
            }
            if (canQuestBeFinished(quest, player)) {
                quest.setFinished(player, true);
                return new Result(true, "quest " + quest.getQuestName() + " finished successfully, " + quest.getReward().getQuantity() + " " + quest.getReward().getItem().getName() + " added to your inventory");
            }else{
                return new Result(false, "insufficient material");
            }
        }
        return new Result(false, "nobody is around");

    }


    private NPC findNPCAround(String name, Player player) {
        NPC npc = parseNPC(name);

        if (npc == null) {
            return null;
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (player.getX() + i == npc.getX() && player.getY() + j == npc.getY()) {
                    return npc;
                }
            }
        }
        return null;
    }

    private ArrayList<String> getNPCList() {
        ArrayList<String> NPCNames = new ArrayList<>();
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            NPCNames.add(npc.getName());
        }
        return NPCNames;
    }

    private void check() {
        for (Player player : App.getGame().getPlayers()) {
            for (NPC npc : App.getGame().getVillage().getNPCs()) {
                if (npc.getDaysToUnlockQ3() <= 0) {
                    npc.getQuests().get(2).setLocked(player, false);
                }
                Random random = new Random();
                if (npc.getFriendShip(player) >= 600 && random.nextBoolean()) {
                    Random rand = new Random();
                    Item item = null;
                    int quantity = 0;
                    int Random = rand.nextInt(100) + 1;
                    if (Random <= 33) {
                        item = npc.getItemsToGift().get(0).getItem();
                        quantity = npc.getItemsToGift().get(0).getQuantity();
                    } else if (Random <= 66) {
                        item = npc.getItemsToGift().get(1).getItem();
                        quantity = npc.getItemsToGift().get(1).getQuantity();
                    } else {
                        item = npc.getItemsToGift().get(2).getItem();
                        quantity = npc.getItemsToGift().get(2).getQuantity();
                    }
                    System.out.println(npc.getName() + " sent you a special gift, " + quantity + " " + item.getName());
                }
            }
        }
    }

    private void reset() {

        for (Player player : App.getGame().getPlayers()) {
            for (NPC npc : App.getGame().getVillage().getNPCs()) {
                npc.setTalkedToday(player, false);
                npc.setGiftedToday(player, false);
                npc.setDaysToUnlockQ3(npc.getDaysToUnlockQ3() - 1);
            }
        }
    }

    private boolean canQuestBeFinished(Quest quest, Player player) {
        Slot slot = player.getInventory().getSlotByItem(quest.getRequest().getItem());
        if (slot == null) {
            return false;
        } else if (slot.getQuantity() < quest.getRequest().getQuantity()) {
            return false;
        }
        player.getInventory().removeFromInventory(quest.getRequest().getItem(), quest.getRequest().getQuantity());
        int quantity = quest.getReward().getQuantity();
        NPC theNPC = null;
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            if (npc.getQuests().contains(quest)) {
                theNPC = npc;
            }
        }
        if (theNPC.getFriendShip(player) >= 400) {
            quantity *= 2;
        }

        return true;
    }

    public Result collectReward(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Inventory inventory = player.getInventory();
        int indexOfQuest = Integer.parseInt(matcher.group(1));
        String npcname = matcher.group(2).trim();
        for (NPC npc : App.getGame().getVillage().getNPCs()) {
            if (!npc.getName().equals(npcname)) continue;
            Quest quest = npc.getQuests().get(indexOfQuest - 1);
            if (quest.getReward() == null || quest.getMoneyPrize() > 0) {
                player.incMoney(quest.getReward().getQuantity());
            } else {
                collectReward(player, quest, quest.getReward().getQuantity());
            }
            return new Result(true, "reward collected");
        }
        return new Result(false, "something went wrong");
    }

    public void collectReward(Player player, Quest quest, int quantity) {
        player.getInventory().addToInventory(quest.getReward().getItem(), quantity);
    }

    public void resetAndCheck() {
        check();
        reset();
    }
}
