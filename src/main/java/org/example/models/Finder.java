package org.example.models;

import java.util.ArrayList;

public class Finder {
    public static User getUserByUsername(String username){
        for (User user : App.users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public static Game getGameByUsername(String username){
        //TODO
        return new Game(new ArrayList<>());
    }
}
