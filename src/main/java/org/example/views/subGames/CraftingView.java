package org.example.views.subGames;

import org.example.controllers.subgames.CraftingController;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class CraftingView {
    static CraftingController controller =  new CraftingController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.ShowCraftingRecipes.getMatcher(input))!=null){
            System.out.println(controller.showRecepies(matcher));;
        }else if((matcher = GameMainCommands.CraftingCraft.getMatcher(input))!=null){
            System.out.println(controller.craftRecipe(matcher));;
        }else if((matcher = GameMainCommands.PlaceItem.getMatcher(input))!=null){
            System.out.println(controller.placeItem(matcher));;
        }else if((matcher = GameMainCommands.ArtisanUse.getMatcher(input))!=null){
            System.out.println(controller.artisanUse(matcher));;
        }else if((matcher = GameMainCommands.ArtisanGet.getMatcher(input))!=null){
            System.out.println(controller.artisanGet(matcher));;
        }
        else{
            return false;
        }
        return true;
    }
}
