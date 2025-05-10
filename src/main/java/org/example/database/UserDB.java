package org.example.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.example.models.*;

import java.util.HashMap;

public class UserDB {
    public static void connect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"); // Keep Client Open
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        System.out.println("reached here");

// Step 1: Load Games
        datastore.find(Game.class).forEach(game -> App.games.add(game));

        System.out.println("games loaded");

// Step 2: NO NEED to manually load Players anymore! Morphia will resolve `@Reference List<Player> players` automatically. 🎉

// Step 3: Load Users & Resolve Their Game References
        datastore.find(User.class).forEach(user -> {
            if (user.getCurrentGame() != null) {
                Game resolvedGame = datastore.find(Game.class)
                        .stream()
                        .filter(game -> game.get_id().equals(user.getCurrentGame().get_id()))
                        .findFirst()
                        .orElse(null);
                user.setCurrentGame(resolvedGame);
            }

            App.userMap.put(user.get_id(), user);
            App.addUser(user);

            System.out.println("User loaded: " + user.getUsername());
        });

        System.out.println("Connected to database: " + datastore.getDatabase().getName());

// Step 4: Load LastUser
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

// Step 5: **Do NOT close MongoClient yet** so other functions can still access it.
    }


    public static void disconnect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        HashMap<User, Game> userGames = new HashMap<>();

        for (User user : App.users) {
            Game _game = user.getCurrentGame();
            userGames.put(user, _game);
            user.setCurrentGame(null);
            datastore.save(user);
        }
//        for (Game game : App.games) {
//            datastore.save(game);
//        }

        for (User user : userGames.keySet()) {
            user.setCurrentGame(userGames.get(user)); // Restore reference
            user.setCurrentGame(null);
            datastore.save(user); // Save updated User
        }


        datastore.getMapper().map(LastUser.class);
        datastore.find(LastUser.class).delete();
        if (App.getCurrentUser() != null) {
            LastUser lu = new LastUser(App.getCurrentUser().get_id());
            datastore.save(lu);
        }
    }

}
