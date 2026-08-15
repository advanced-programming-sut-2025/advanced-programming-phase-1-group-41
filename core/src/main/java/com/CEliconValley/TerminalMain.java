package com.CEliconValley;

import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.items.CookingRecipe;
import com.CEliconValley.views.AppView;

import java.security.NoSuchAlgorithmException;

public class TerminalMain {
    public static void main(String[] args) {
        try {
            CookingRecipe.updateRecipe();
            UserDB.connect();
            App.setupServer();
            System.out.println("users "+App.users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
