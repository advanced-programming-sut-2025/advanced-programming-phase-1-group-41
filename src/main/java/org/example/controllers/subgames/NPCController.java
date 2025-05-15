package org.example.controllers.subgames;

import org.example.models.*;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Inventory;
import org.example.models.items.Item;
import org.example.models.npc.npcCharacters.NPC;
import org.example.models.npc.npcCharacters.Quest;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import static org.example.models.Finder.parseItem;
import static org.example.models.Finder.parseNPC;

public class NPCController {

    public Result meetNpc(Matcher matcher){
        Inventory inventory=App.getGame().getCurrentPlayer().getInventory();
        Player player=App.getGame().getCurrentPlayer();
        String npcName = matcher.group(1);
        NPC npc=findNPCAround(npcName);
        if(npc==null){
            if(!getNPCList().contains(npcName)){
                return new Result(false, "no one`s here with that name");
            }
            return new Result(false,  npcName+" is not around");
        }
        if(!npc.isTalkedToday(player)){
            npc.incFriendShip(player,20);
            npc.setTalkedToday(player,true);
            npc.getQuests().get(0).setLocked(player,false);
        }
        if(!App.getGame().getWeatherType().equals(WeatherType.Sunny)){
            switch (App.getGame().getWeatherType()){
                case Rainy:
                    return new Result(true, npc.getDialogues(4));
                case Snowy:
                    return new Result(true, npc.getDialogues(5));
                case Stormy:
                    return new Result(true, npc.getDialogues(6));
            }
        }else if(App.getGame().getTime().getHour()>18){
            return new Result(true, npc.getDialogues(7));
        }else if(npc.getFriendShip(player)>=600){
            return new Result(true, npc.getDialogues(8));
        }else{
            switch (App.getGame().getTime().getSeason()){
                case Spring:
                    return new Result(true, npc.getDialogues(0));
                case Summer:
                    return new Result(true, npc.getDialogues(1));
                case Autumn:
                    return new Result(true, npc.getDialogues(2));
                case Winter:
                    return new Result(true, npc.getDialogues(3));
            }
        }
        return new Result(false, "impossible");
    }

    public Result giftToNpc(Matcher matcher){
        Inventory inventory=App.getGame().getCurrentPlayer().getInventory();
        Player player=App.getGame().getCurrentPlayer();
        String npcName = matcher.group(1);
        String itemName = matcher.group(2);
        NPC npc=findNPCAround(npcName);
        if(npc==null){
            if(!getNPCList().contains(npcName)){
                return new Result(false, "no one`s here with that name");
            }
            return new Result(false,  npcName+" is not around");
        }
        Item item=parseItem(itemName);
        if(item==null){
            return new Result(false, "no item with that name");
        }
        if(inventory.getSlotByItem(item)==null){
            return new Result(false, "you dont have "+itemName);
        }
        if((item instanceof Tool || item instanceof CraftableMachine)){
            return new Result(false, "tools or Craftable machines can not be gifted");
        }
        inventory.removeFromInventory(item,1);
        if(npc.getFavorites().contains(item)){
            if(!npc.isGiftedToday(player)) {
                npc.incFriendShip(App.getGame().getCurrentPlayer(), 200);

            }
            npc.setGiftedToday(player,true);
            return new Result(true, npc.getDialogues(10));
        }
        else{
            if(!npc.isGiftedToday(player)){
                npc.incFriendShip(App.getGame().getCurrentPlayer(), 50);
            }
            npc.setGiftedToday(player,true);
            return new Result(true, npc.getDialogues(9));
        }
    }

    public Result friendshipList(Matcher matcher){
        Inventory inventory=App.getGame().getCurrentPlayer().getInventory();
        Player player=App.getGame().getCurrentPlayer();
        for(NPC npc :App.getGame().getVillage().getNPCs()){
            String levelOfFriendship ="";
            int levelOfFriendshipNumber=-1 ;
            if(npc.getFriendShip(player)<200){
                levelOfFriendship="knows";
                levelOfFriendshipNumber=0;
            }
            else if(npc.getFriendShip(player)<400){
                levelOfFriendship="likes";
                levelOfFriendshipNumber=1;
            }
            else if(npc.getFriendShip(player)<600){
                levelOfFriendship="admires";
                levelOfFriendshipNumber=2;
            }
            else if(npc.getFriendShip(player)<800){
                levelOfFriendship="loves";
                levelOfFriendshipNumber=3;
            }
            System.out.println(npc.getName()+" "+levelOfFriendship+" you (friendShip Level : "+levelOfFriendshipNumber+" ["+npc.getFriendShip(player)+"] )");
        }
        return new Result(true, " ");
    }

    public Result questList(Matcher matcher){
        Inventory inventory=App.getGame().getCurrentPlayer().getInventory();
        Player player=App.getGame().getCurrentPlayer();
        for(NPC npc :App.getGame().getVillage().getNPCs()){
            System.out.println(Colors.foreColor(15)+npc.getName()+"`s quests :"+Colors.RESET);
            for(Quest quest:npc.getQuests()){
                if(quest.isLocked(player)){
                    System.out.println(Colors.foreColor(124)+"quest "+quest.getQuestName()+" is locked"+Colors.RESET);
                }
                else{
                    System.out.printf(Colors.foreColor(46)+"quest "+quest.getQuestName()+" -> "+quest.getQuestPreTalk()+" | objective : deliver "+quest.getRequest().getQuantity()+" "+quest.getRequest().getItem().getName()+" to "+npc.getName()+Colors.RESET);
                    if(quest.getMoneyPrize()!=0.0){
                        System.out.printf(Colors.foreColor(46)+" | reward : "+quest.getMoneyPrize()+" gold\n"+Colors.RESET);
                    }
                    else if(quest.getFriendShip()!=0){
                        System.out.printf(Colors.foreColor(46)+" | reward : "+quest.getFriendShip()+" points of friendship\n"+Colors.RESET);
                    }
                    else if(quest.getCookingRecipe()!=null){
                        System.out.printf(Colors.foreColor(46)+" | reward : "+quest.getCookingRecipe().getName()+"\n"+Colors.RESET);
                    }
                    else{
                        System.out.printf(Colors.foreColor(46)+" | reward : "+quest.getReward().getQuantity()+" "+quest.getReward().getItem().getName()+"\n"+Colors.RESET);
                    }
                }
            }
        }
        return new Result(true, " ");
    }

    public Result finishQuest(Matcher matcher){return null;}

    public void increaseFriendshipLevel(){}

    public void ReceiveGiftFromNpc(){}

    private NPC findNPCAround(String name){
        Player player = App.getGame().getCurrentPlayer();
        NPC npc = parseNPC(name);

        if(npc==null){
            return null;
        }
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if (player.getX() + i == npc.getX() && player.getY() + j == npc.getY()) {
                    return npc;
                }
            }
        }
        return null;
    }
    private ArrayList<String> getNPCList(){
        ArrayList<String> NPCNames = new ArrayList<>();
        for(NPC npc:App.getGame().getVillage().getNPCs()){
            NPCNames.add(npc.getName());
        }
        return NPCNames;
    }
    private void check(){
      for(Player player : App.getGame().getPlayers()) {
          for (NPC npc : App.getGame().getVillage().getNPCs()) {
              if (npc.getDaysToUnlockQ3() <= 0) {
                  npc.getQuests().get(2).setLocked(player, false);
              }
          }
      }
    }
    private void reset(){

        for(Player player : App.getGame().getPlayers()) {
            for (NPC npc : App.getGame().getVillage().getNPCs()) {
                npc.setTalkedToday(player,false);
                npc.setGiftedToday(player,false);
                npc.setDaysToUnlockQ3(npc.getDaysToUnlockQ3()-1);
            }
        }
    }
    public void resetAndCheck(){
        check();
        reset();
    }
}
