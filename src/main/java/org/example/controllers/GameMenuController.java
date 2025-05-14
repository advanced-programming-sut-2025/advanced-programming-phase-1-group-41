package org.example.controllers;

import org.example.models.*;
import org.example.models.locations.Farm;
import org.example.models.npc.NPC;
import org.example.views.GameMenu;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;

public class GameMenuController {


    public ArrayList<ArrayList<Player>> buildFriendships(){
        return null;
    }

    public HashMap<NPC, Integer> buildFriendshipsWithNPCs(){
        return null;
    }

    public Result gameMap(Matcher matcher){ return null ;}


    public Result saveGame(Matcher matcher){ return null ;}

    public Result exitGame(Matcher matcher){
        if(!App.getGame().getLoader().equals(App.getGame().getCurrentPlayer())){
            return new Result(false,"no exit for you mate");
        }
        // save the game
        App.setGame(null);
        App.setMenu(Menu.Main);
        return new Result(true,"cya") ;
    }

    public Result deleteGame(Matcher matcher, Scanner scanner){
        boolean success = GameMenu.handleDeleteGame(scanner);
        if(success){
            for (Player player : App.getGame().getPlayers()) {
                player.getUser().setCurrentGame(null);
            }
            App.setGame(null);
            // TODO delete from db
            App.setMenu(Menu.Main);
            return new Result(true, "game deleted successfully," +
                    "returning to main menu...");
        }
        return new Result(false,"the show must go on..");
    }

    public Result nextTurn(Matcher matcher){
        ArrayList<Player> players = App.getGame().getPlayers();
        System.out.println(App.getGame().getCurrentPlayer());
        System.out.println(players);
        for(int i=0;i<4;i++){
            if(players.get(i).equals(App.getGame().getCurrentPlayer())){
                App.getGame().setCurrentPlayer(players.get((i+1)%4));
                App.getGame().setRoundEnergy(0);
                printNewMessages(App.getGame().getCurrentPlayer());
                if(i == 3){
                    App.getGame().getTime().advanceOneHour();
                }
                break;
            }
        }
        return new Result(true,"next turn.. : "+App.getGame().getCurrentPlayer());
    }
    private void printNewMessages(Player player){
        System.out.println("turn is : "+App.getGame().getCurrentPlayer());
        int a = 0;
        for(Friendship friendship : App.getGame().getCurrentPlayer().getFriendships()){
            a++;
        }
        if(a > 0){
            System.out.println("New messages:");
        }
        for(Friendship friendship : App.getGame().getCurrentPlayer().getFriendships()){
            System.out.println(friendship.newMessages());
            friendship.setLastReadMessage();
        }
        if(!player.getNewGifts().isEmpty()){
            System.out.println("New gifts:");
        }
        for(Gift gift : player.getNewGifts()){
            System.out.println(gift.toString());
        }
    }

    public Result tradeMenu(Matcher matcher){ return null ;}
}
