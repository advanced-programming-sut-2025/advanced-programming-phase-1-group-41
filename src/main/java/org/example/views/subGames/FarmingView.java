package org.example.views.subGames;

import org.example.controllers.subgames.FarmingController;
import org.example.models.App;
import org.example.models.Finder;
import org.example.models.tools.WateringCan;
import org.example.views.commands.gameCommands.FarmingCommands;
import org.example.views.commands.gameCommands.GameMainCommands;

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
