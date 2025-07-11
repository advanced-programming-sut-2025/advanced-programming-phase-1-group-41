package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.CookingController;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class CookingView {
    static CookingController controller =  new CookingController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.CookingShowRecipes.getMatcher(input))!=null){
            System.out.println(controller.showRecepies(matcher));;
        }else if((matcher = GameMainCommands.CookingRefrigerator.getMatcher(input))!=null){
            System.out.println(controller.cookingRef(matcher));;
        }else if((matcher = GameMainCommands.ShowRefrigerator.getMatcher(input))!=null){
            System.out.println(controller.showRef(matcher));;
        }else if((matcher = GameMainCommands.CookingPrepare.getMatcher(input))!=null){
            System.out.println(controller.prepareFood(matcher));;
        }
        else{
            return false;
        }
        return true;
    }
}
