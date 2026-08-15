package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.CraftingController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Result;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class CraftingView {
    static CraftingController controller =  new CraftingController();
    public static boolean check(String input, String playername){
        Matcher matcher;
        if((matcher = GameMainCommands.ShowCraftingRecipes.getMatcher(input))!=null){
            System.out.println(controller.showRecepies(matcher, playername));;
        }else if((matcher = GameMainCommands.CraftingCraft.getMatcher(input))!=null){
            System.out.println(controller.craftRecipe(matcher, playername));;
        }else if((matcher = GameMainCommands.PlaceItem.getMatcher(input))!=null){
            System.out.println(controller.placeItem(matcher, playername));;
        }else if((matcher = GameMainCommands.ArtisanUse.getMatcher(input))!=null){
            Result result = controller.artisanUse(matcher, playername);
            System.out.println(result);
            if(!result.success()){
                App.sendResult(result, playername);
            }
        }else if((matcher = GameMainCommands.ArtisanGet.getMatcher(input))!=null){
            System.out.println(controller.artisanGet(matcher, playername));;
        }else if((matcher = GameMainCommands.ArtisanStart.getMatcher(input))!=null){
            Result result = controller.artisanStart(matcher, playername);
            System.out.println(result);
            if(!result.success()){
                App.sendResult(result, playername);
            }
        }else if((matcher = GameMainCommands.ArtisanCheat.getMatcher(input))!=null){
            System.out.println(controller.artisanCheat(matcher, playername));;
        }else if((matcher = GameMainCommands.ArtisanStop.getMatcher(input))!=null){
            System.out.println(controller.artisanStop(matcher, playername));;
        }
        else{
            return false;
        }
        return true;
    }
}
