package org.example.controllers;

import org.example.models.Player;
import org.example.models.Result;
import org.example.models.npc.NPC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {

    public Result newGame(Matcher matcher){}

    public ArrayList<ArrayList<Player>> buildFriendships(){}

    public HashMap<NPC, Integer> buildFriendshipsWithNPCs(){}

    public Result gameMap(Matcher matcher){}

    public Result loadGame(Matcher matcher){}

    public Result saveGame(Matcher matcher){}

    public Result exitGame(Matcher matcher){}

    public Result deleteGame(Matcher matcher){}

    public Result nextTurn(Matcher matcher){}

    public Result tradeMenu(Matcher matcher){}
}
