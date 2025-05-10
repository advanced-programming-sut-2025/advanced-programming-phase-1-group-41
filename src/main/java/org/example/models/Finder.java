package org.example.models;

import org.bson.types.ObjectId;

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
//        return new Game(new ArrayList<>());
        return null;
    }

    public static User getUserById(ObjectId id){
        for (User user : App.users) {
            if(user.get_id().equals(id)){
                return user;
            }
        }
        return null;
    }
}
