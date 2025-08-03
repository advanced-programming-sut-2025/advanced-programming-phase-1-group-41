package com.CEliconValley.models;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.GameMessage;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Lobby {
    private String lobbyName;
    private String lobbyID;
    private boolean isPrivate;
    private String password;
    private Set<String> playerNames;
    private String admin;
    private boolean isVisible;
    long lastTimeJoined;

    public Lobby() {
    }

    public Lobby(String lobbyName, String password, String admin, boolean isVisible, WebSocket conn) {
        this.isPrivate = true;
        this.password = password;
        this.isVisible = isVisible;
        makeLobby(lobbyName, admin, conn);
    }

    public Lobby(String lobbyName, String admin, boolean isVisible, WebSocket conn){
        this.isPrivate = false;
        this.password = null;
        this.isVisible = isVisible;
        makeLobby(lobbyName, admin, conn);
    }

    private void makeLobby(String lobbyName, String admin, WebSocket conn){
        this.lobbyID = giveID();
        this.lobbyName = lobbyName;
        this.playerNames = Collections.synchronizedSet(new HashSet<>());
        this.admin = admin;
        App.lobbies.add(this);
        addPlayer(admin);
        try{
            Thread.sleep(500);
        } catch (Exception e){
            e.printStackTrace();
        }
        GameMessage<Lobby> response = new GameMessage<>("join-lobby",this);
        conn.send(new Gson().toJson(response));
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
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        sendDetails();
    }

    public void sendDetails(){
        GameMessage<Lobby> msg = new GameMessage<>("new-lobby", this);
        String json = new Gson().toJson(msg);
        App.getServer().broadcast(json);
        System.out.println("Server: " + msg);
    }

    public Result addPlayer(String playerName) {
        if(playerNames.size() >= 4){
            return new Result(false, "size-limit");
        }
        playerNames.add(playerName);
        this.lastTimeJoined = System.currentTimeMillis();
        try{
            Thread.sleep(100);
        } catch (Exception e){}
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
            try{
                admin = playerNames.iterator().next();
            } catch (Exception e){
                return new Result(false,"empty");
            }
        }
        try{
            Thread.sleep(100);
        } catch (Exception e){}
        sendDetails();
        return new Result(true, "player-removed");
    }

    public String getAdmin() {
        return admin;
    }

    public long getLastTimeJoined() {
        return lastTimeJoined;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(lobbyID, lobby.lobbyID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lobbyID);
    }

    @Override
    public String toString() {
        return "Lobby{" +
            "admin='" + admin + '\'' +
            ", lobbyName='" + lobbyName + '\'' +
            ", lobbyID='" + lobbyID + '\'' +
            ", isPrivate=" + isPrivate +
            ", password='" + password + '\'' +
            ", playerNames=" + playerNames +
            ", isVisible=" + isVisible +
            ", lastTimeJoined=" + lastTimeJoined +
            '}';
    }
}
