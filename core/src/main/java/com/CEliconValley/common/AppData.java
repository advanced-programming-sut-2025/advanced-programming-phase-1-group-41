package com.CEliconValley.common;

import java.util.HashSet;
import java.util.Set;

public class AppData {
    public Set<OnlineData> onlinePlayers;

    public AppData(Set<OnlineData> onlinePlayers) {
        this.onlinePlayers = new HashSet<>(onlinePlayers);
    }
}
