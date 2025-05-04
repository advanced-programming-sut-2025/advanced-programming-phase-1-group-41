package org.example.controllers;

import org.example.models.Player;
import org.example.models.Result;
import org.example.models.npc.NPC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {

    public Result newGame(Matcher matcher){return null;}

    public ArrayList<ArrayList<Player>> buildFriendships(){return null;}

    public HashMap<NPC, Integer> buildFriendshipsWithNPCs(){return null;}

    public Result gameMap(Matcher matcher){return null;}

    public Result loadGame(Matcher matcher){return null;}

    public Result saveGame(Matcher matcher){return null;}

    public Result exitGame(Matcher matcher){return null;}

    public Result deleteGame(Matcher matcher){return null;}

    public Result nextTurn(Matcher matcher){return null;}

    public Result tradeMenu(Matcher matcher){return null;}
}
