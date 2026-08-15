package com.CEliconValley.common;

import com.CEliconValley.models.Lobby;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppData {
    public Set<OnlineData> onlinePlayers;
    public AppData(Set<OnlineData> onlinePlayers) {
        this.onlinePlayers = new HashSet<>(onlinePlayers);
    }
}
