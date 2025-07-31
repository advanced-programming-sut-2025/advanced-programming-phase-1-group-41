package com.CEliconValley.models;

import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.common.messages.GameMessage;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Lobby {
    private String lobbyName;
    private String lobbyID;
    private boolean isPrivate;
    private String password;
    private Set<String> playerNames;
    private String admin;
    private boolean visible;
    long lastTimeJoined;

    public Lobby() {
    }

    public Lobby(String lobbyName, String password, String admin) {
        makeLobby(lobbyName, admin);
        this.isPrivate = true;
        this.password = password;
    }

    public Lobby(String lobbyName, String admin){
        makeLobby(lobbyName, admin);
        this.isPrivate = false;
        this.password = null;
    }

    private void makeLobby(String lobbyName, String admin){
        this.lobbyID = giveID();
        this.lobbyName = lobbyName;
        this.playerNames = Collections.synchronizedSet(new HashSet<>());
        this.admin = admin;
        App.lobbies.add(this);
        addPlayer(admin);
    }
    private String giveID() {
        int number;
        number = MathUtils.random(10000, 99999);
        for (Lobby lobby : App.lobbies) {
            String theID = String.valueOf(number);
            if (lobby.getLobbyID().equals(theID)) {
                return giveID();
            }
        }
        return String.valueOf(number);
    }


    public boolean isPrivate() {
        return isPrivate;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getPlayerNames() {
        return playerNames;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        sendDetails();
    }

    public void sendDetails(){
        GameMessage<Lobby> msg = new GameMessage<>("new-lobby", this);
        String json = new Gson().toJson(msg);
        App.getServer().broadcast(json);
    }

    public Result addPlayer(String playerName) {
        if(playerNames.size() >= 4){
            return new Result(false, "size-limit");
        }
        playerNames.add(playerName);
        this.lastTimeJoined = System.currentTimeMillis();

        sendDetails();
        // boolean isMoreThanFiveMinutes = (currentMillis - lastJoinedMillis) > 5 * 60 * 1000;
        // TODO to check weather it's been 5 mins! in render of gdx

        return new Result(true, "player-added");
    }
    public Result removePlayer(String playerName) {
        if(!playerNames.contains(playerName)){
            return new Result(false,"404");
        }
        playerNames.remove(playerName);
        if(admin.equals(playerName)){
            admin = playerNames.iterator().next();
        }
        if(admin == null){
            return new Result(false,"empty");
        }
        sendDetails();
        return new Result(true, "player-removed");
    }
}
