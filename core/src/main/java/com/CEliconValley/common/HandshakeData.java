package com.CEliconValley.common;

import com.CEliconValley.models.Lobby;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HandshakeData {
    ArrayList<Lobby> currentLobbies;
    ArrayList<GameData> currentGames;
    Set<OnlineData> onlinePlayers;

    public HandshakeData(ArrayList<GameData> currentGames, ArrayList<Lobby> currentLobbies,
                         Set<OnlineData> onlinePlayers) {
        this.currentGames = currentGames;
        this.currentLobbies = currentLobbies;
        this.onlinePlayers = new HashSet<>(onlinePlayers);
    }

    public ArrayList<GameData> getCurrentGames() {
        return currentGames;
    }

    public ArrayList<Lobby> getCurrentLobbies() {
        return currentLobbies;
    }

    public Set<OnlineData> getOnlinePlayers() {
        return onlinePlayers;
    }
}
