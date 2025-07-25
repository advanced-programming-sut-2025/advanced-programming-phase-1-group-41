package com.CEliconValley.database;

import com.CEliconValley.models.*;
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

            datastore.getMapper().map(TimeLine.class);
            datastore.getMapper().map(Player.class);
            datastore.getMapper().map(Game.class);
            datastore.getMapper().map(User.class);

        System.out.println("reached here");

        // Step 1: Load Users FIRST (Store Only ObjectIds Initially)


        HashMap<ObjectId, User> userMap = new HashMap<>();
        datastore.find(User.class).forEach(user -> userMap.put(user.get_id(), user));

        System.out.println("users loaded");

        // Step 2: Load Players & Resolve User References


        HashMap<ObjectId, Player> playerMap = new HashMap<>();
        datastore.find(Player.class).forEach(player -> {
            if (player.getUserId() != null) {
                player.setUser(userMap.get(player.getUserId())); // Restore User reference
            } else {
                System.out.println("Warning: Player " + player.get_id() + " has null UserId in DB!");
            }

            playerMap.put(player.get_id(), player);
        });

// Debug check
        for (Player player : playerMap.values()) {
            if (player.getUser() == null) {
                System.out.println("Warning: Player " + player.get_id() + " still has null User reference.");
            } else {
                System.out.println("Debug: Player " + player.get_id() + " assigned to User " + player.getUser().getUsername());
            }
        }

        System.out.println("players loaded");

        for (Player player : playerMap.values()) {
            System.out.println(player.getUserId());
        }

        // Step 3: Load Games & Resolve Player References


        HashMap<ObjectId, Game> gameMap = new HashMap<>();
        datastore.find(Game.class).forEach(game -> {
            List<Player> resolvedPlayers = game.getPlayersId() != null
                    ? game.getPlayersId().stream().map(playerMap::get).collect(Collectors.toList())
                    : new ArrayList<>();

            game.setPlayers(resolvedPlayers);


            game.setCurrentPlayer(playerMap.get(game.getCurrentPlayerId()));
            game.setLoader(playerMap.get(game.getLoaderId()));

            gameMap.put(game.get_id(), game);
            App.games.add(game);
        });

        System.out.println("games loaded");

        // Step 4: Assign Users’ Current Games

        datastore.find(User.class).forEach(user -> {
            System.out.println("Debug: Loaded user " + user.getUsername() + " with stored gameId: " + user.getGameId());

            if (user.getGameId() != null) {
                user.setCurrentGame(gameMap.get(user.getGameId()));
            }

            App.userMap.put(user.get_id(), user);
            App.addUser(user);
        });

        System.out.println("Connected to database: " + datastore.getDatabase().getName());

        // Step 5: Load LastUser
        datastore.getMapper().map(LastUser.class);
        LastUser lu = datastore.find(LastUser.class).first();
        if (lu != null) {
            User user = Finder.getUserById(lu.getUserId());
            if (user != null && user.isStayLoggedIn()) {
                System.out.println("Logged user: " + user);
                App.setMenu(Menu.Main);
                App.setCurrentUser(user);
            } else {
                System.out.println("User doesn't want to stay logged in.");
            }
        }

        gameMap.values().forEach(game -> {
            System.out.println(game.getPlayers());
        });

        System.out.println("D:");


    }


    public static void disconnect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        for (Game game : App.games) {
            if (game.getPlayers() != null) {
                for (Player player : game.getPlayers()) {
                    player.prepareForSaving();
                    datastore.save(player);
                }
            } else {
                System.out.println("Warning: Game " + game.get_id() + " has no players.");
            }
        }
        System.out.println("Players saved");

        // Step 2: Save Users Without Their Current Game (To Avoid Saving Full Object)
        for (User user : App.users) {
            if (user.getCurrentGame() != null) {
                System.out.println("Debug: User " + user.getUsername() + " has currentGame ID: " + user.getCurrentGame().get_id());
                System.out.println("Debug: User " + user.getUsername() + " has currentGame: " + user.getCurrentGame());
            } else {
                System.out.println("Warning: User " + user.getUsername() + " has null currentGame before saving!");
            }

            user.prepareForSaving();
//            user.setGameId(null);
            datastore.save(user);
        }
        System.out.println("Users saved");

        // Step 3: Prepare and Save Each Game
        HashMap<ObjectId, Game> gameMap = new HashMap<>();
        for (Game game : App.games) {
            System.out.println("Debug: Saving game with id " + game.get_id());
            game.prepareForSaving();
            System.out.println("Debug: Saving game. Time before save - Hour: " + game.getTime().getHour());
            datastore.save(game);
            gameMap.put(game.get_id(), game);
        }
        System.out.println("Games saved");

        // Step 4: Restore User References After Saving
        for (User user : App.users) {
            if (user.getGameId() != null) {
                Game resolvedGame = gameMap.get(user.getGameId());
                if (resolvedGame == null) {
                    System.out.println("Warning: No matching game found for user " + user.getUsername());
                }
                user.setCurrentGame(resolvedGame);
//                user.setCurrentGame(null);
            }
            datastore.save(user); // Save updated User with restored reference
        }

        System.out.println("User references restored");

        // Step 5: Save Last User Session
        datastore.getMapper().map(LastUser.class);
        datastore.find(LastUser.class).delete();
        if (App.getCurrentUser() != null) {
            LastUser lu = new LastUser(App.getCurrentUser().get_id());
            datastore.save(lu);
        }

        System.out.println("LastUser saved");





    }

}
