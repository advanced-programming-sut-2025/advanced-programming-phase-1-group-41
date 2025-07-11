package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.FarmingController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.tools.WateringCan;
import com.CEliconValley.views.commands.gameCommands.FarmingCommands;

import java.util.regex.Matcher;

public class FarmingView {
    static FarmingController controller = new FarmingController();
    public static boolean check(String input){
        Matcher matcher;
        if ((matcher = FarmingCommands.CraftInfo.getMatcher(input)) != null) {
            System.out.println(controller.craftInfo(matcher));
        }else if ((matcher = FarmingCommands.GreenhouseBuild.getMatcher(input)) != null) {
            System.out.println(controller.buildGreenhouse(matcher));
        }else if((matcher = FarmingCommands.Plant.getMatcher(input)) != null){
            System.out.println(controller.plant(matcher));
        } else if((matcher = FarmingCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(controller.showPlant(matcher));
        } else if((matcher = FarmingCommands.HowMuchWater.getMatcher(input)) != null){
            if(App.getGame().getCurrentPlayer().getInventory().getSlotByItem(Finder.getToolByName("WateringCan")).getItem() instanceof WateringCan){
                System.out.println("Water left: " + ((WateringCan) App.getGame().getCurrentPlayer().getInventory().getSlotByItem(Finder.getToolByName("WateringCan")).getItem()).getTiles());
            }
        } else if((matcher = FarmingCommands.Fertilize.getMatcher(input)) != null){
            System.out.println(controller.fertilize(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
