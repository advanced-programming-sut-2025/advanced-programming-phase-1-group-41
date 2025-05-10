package org.example.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.example.models.*;

public class UserDB {
    public static void connect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"); // Keep MongoClient open
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        System.out.println("Connected to database: " + datastore.getDatabase().getName());
        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        datastore.find(User.class).forEach(user -> {
            System.out.println(user);
            if (user.getCurrentGame() != null) {
                System.out.println("User's game: " + user.getCurrentGame());
            }
            App.addUser(user);
        });

        datastore.getMapper().map(LastUser.class);
        LastUser lu = datastore.find(LastUser.class).first();
        if(lu != null){
            User user = Finder.getUserById(lu.getUserId());
            if(user == null){
                return;
            }
            if(user.isStayLoggedIn()){
                System.out.println("logged user: "+user);
                App.setMenu(Menu.Main);
                App.setCurrentUser(user);
            }else{
                System.out.println("he doesn't want to be logged in :(");
            }
        }

        mongoClient.close(); // Close the connection only after all operations are done
    }

    public static void disconnect(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");
        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);
        for (User user : App.users) {
            datastore.save(user);
        }
        datastore.getMapper().map(LastUser.class);
        datastore.find(LastUser.class).delete();
        if(App.getCurrentUser() != null){
            LastUser lu = new LastUser(App.getCurrentUser().get_id());
            datastore.save(lu);
        }
    }

}
