package org.example.views.subGames;

import org.example.controllers.CookingController;
import org.example.controllers.MapController;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class CookingView {
    static CookingController controller =  new CookingController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.CookingShowRecipes.getMatcher(input))!=null){
            controller.showRecepies(matcher);
        }
        else{
            return false;
        }
        return true;
    }
}
