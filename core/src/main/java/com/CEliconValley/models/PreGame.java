package com.CEliconValley.models;

import com.CEliconValley.models.locations.FarmType;
import com.CEliconValley.server.handlers.GameHandler;

import java.util.ArrayList;

public class PreGame {
    ArrayList<Player> players = new ArrayList<>();
    Player admin = null;
    String adminName;
    int counter = 0;
    int playersSize;
    Lobby lobby;
    public PreGame(int size, String adminName, Lobby lobby) {
        playersSize = size;
        this.adminName = adminName;
        this.lobby = lobby;
    }

    public void addPlayer(String username, FarmType farmType) {
        Player player = new Player(Finder.getUserByUsername(username), farmType, counter);
        players.add(player);
        counter++;
        if(adminName.equals(username)) {
            admin = player;
        }
        if(counter == playersSize){
            GameHandler.newGame();
        }
    }


    public Player getAdmin() {
        return admin;
    }

    public String getAdminName() {
        return adminName;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayersSize() {
        return playersSize;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
