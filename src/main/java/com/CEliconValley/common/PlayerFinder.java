package com.CEliconValley.common;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.User;
import com.CEliconValley.models.locations.Farm;

import java.util.ArrayList;

public class PlayerFinder {
    public static Player getPlayerByName(ArrayList<Player> players, String playerName) {
        for (Player player : players) {
            if(player.getUser().getUsername().equals(playerName)){
                return player;
            }
        }
        return null;
    }
    public static User getUserByPlayerName(String name){
        for (User user : App.getUsers()) {
            if(user.getUsername().equals(name)) return user;
        }
        return null;
    }

    public static Player getPlayerByFarm(ArrayList<Player> players, int farmID){
        for (Player player : players) {
            if(player.getFarmId() == farmID){
                return player;
            }
        }
        return null;
    }

}
