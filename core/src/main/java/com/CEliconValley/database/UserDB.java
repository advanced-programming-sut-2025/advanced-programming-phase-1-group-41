package com.CEliconValley.database;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.models.*;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserDB {
    public static void connect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.find(User.class).forEach(user -> {
            App.addUser(user);
        });
        datastore.find(GameData.class).forEach(gameData -> {
            App.gamesdata.add(gameData);
        });
    }


    public static void saveGame(Game game){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        GameData gameData = new GameData(game);
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(gameData);
//        App.getServer().broadcast(jsonData);
        if(!App.gamesdata.contains(gameData)){
            App.gamesdata.add(gameData);
        }
        datastore.save(gameData);
    }
    public static void deleteGame(Game game){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        GameData gameData = new GameData(game);
        datastore.delete(gameData);
        if(App.gamesdata.contains(gameData)){
            App.gamesdata.remove(gameData);
        }
    }

    public static Game loadGame(String username){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        for (GameData gameData : datastore.find(GameData.class)) {
            for (PlayerData pd : gameData.getPlayersData()) {
                if(pd.getUsername().equals(username)){
                    return gameData.makeGame();
                }
            }
        }
        return null;
    }


    public static void saveUser(User user){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.save(user);
    }
    public static void deleteUser(User user){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.delete(user);
    }

    public static void disconnect(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        for (User user : App.getUsers()) {
            datastore.save(user);
        }
    }


}
