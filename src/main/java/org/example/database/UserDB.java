package org.example.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.models.App;
import org.example.models.User;

import java.io.File;
import java.io.IOException;

public class UserDB {
    public static void connect() {
        // Connect to MongoDB Server
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Access the database
            MongoDatabase database = mongoClient.getDatabase("ProjectDB");
            System.out.println("Connected to database: " + database.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveUserData(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("database/users/"+user.getUsername()+".json"), user);
    }
    public static void updateUsers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        for (User user : App.users) {
            saveUserData(user);
        }
    }

    public static User loadUserData(String username) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File("database/users/"+username+".json"), User.class);
        return user;
    }
}
