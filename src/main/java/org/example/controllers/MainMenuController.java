package org.example.controllers;

import org.example.models.App;
import org.example.models.Menu;
import org.example.models.Result;

import java.util.regex.Matcher;

public class MainMenuController {
    public void logout(){
        App.setCurrentUser(null);
        App.setMenu(Menu.Authentication);
    }
    public Result enterMenu(Matcher matcher){
        if(!matcher.matches()) {
            return new Result(false, "Invalid command!");
        }
        String menuName = matcher.group("menuName");
        if(menuName.equalsIgnoreCase("Profile")){
            App.setMenu(Menu.Profile);
            return new Result(true, "Redirecting to Profile Menu...");
        } else if(menuName.equalsIgnoreCase("Authentication") || menuName.equalsIgnoreCase("Auth")){
            App.setMenu(Menu.Authentication);
            return new Result(true, "Redirecting to Authentication Menu...");
        }
        return new Result(false, "Invalid menu name!\nMenu options:\nProfile\nAuthentication");
    }
}
