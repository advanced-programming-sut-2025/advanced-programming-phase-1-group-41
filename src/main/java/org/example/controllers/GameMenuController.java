package org.example.controllers;

import org.example.models.*;
import org.example.models.locations.Farm;
import org.example.models.npc.NPC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {

    public Result newGame(Matcher matcher){
        if(matcher.group("flag")==null){
            return new Result(false,"Empty flag");
        }
        if(matcher.group("username3")==null){
            return new Result(false,"Fewer than 3 players");
        }
        if(matcher.group("trash")!=null&&!matcher.group("trash").trim().isEmpty()){
            return new Result(false,matcher.group("More than 3 players"));
        }
        if(Finder.getUserByUsername(matcher.group("username1"))==null||
           Finder.getUserByUsername(matcher.group("username2"))==null||
           Finder.getUserByUsername(matcher.group("username3"))==null
        ){
            return new Result(false,matcher.group("Invalid Username"));
        }
        if(Finder.getGameByUsername(matcher.group("username1"))!=null||
           Finder.getGameByUsername(matcher.group("username2"))!=null||
           Finder.getGameByUsername(matcher.group("username3"))!=null){
            return new Result(false,matcher.group("Username already in use"));
        }
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player(App.getCurrentUser()));
        players.add(new Player(Finder.getUserByUsername(matcher.group("username1"))));
        players.add(new Player(Finder.getUserByUsername(matcher.group("username2"))));
        players.add(new Player(Finder.getUserByUsername(matcher.group("username3"))));

        new Game(players);

        return new Result(true,"Game created successfully");

    }
    public Result selectMap(Matcher matcher){
        if(!matcher.group("mapNumber").matches("\\d+")||Integer.parseInt(matcher.group("mapNumber"))<=0||Integer.parseInt(matcher.group("mapNumber"))>=4){
            return new Result(false,matcher.group("Invalid number"));
        }

        App.getGame().getCurrentPlayer().setFarm(new Farm(Integer.parseInt(matcher.group("mapNumber"))));
        App.getGame().passTurn();
        return new Result(true,"Selected"+matcher.group("mapNumber"));
    }

    public ArrayList<ArrayList<Player>> buildFriendships(){
        return null;
    }

    public HashMap<NPC, Integer> buildFriendshipsWithNPCs(){
        return null;
    }

    public Result gameMap(Matcher matcher){ return null ;}

    public Result loadGame(Matcher matcher){ return null ;}

    public Result saveGame(Matcher matcher){ return null ;}

    public Result exitGame(Matcher matcher){ return null ;}

    public Result deleteGame(Matcher matcher){ return null ;}

    public Result nextTurn(Matcher matcher){ return null ;}

    public Result tradeMenu(Matcher matcher){ return null ;}
}
