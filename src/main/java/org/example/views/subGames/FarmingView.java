package org.example.views.subGames;

import org.example.controllers.FarmingController;
import org.example.views.commands.gameCommands.FarmingCommands;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class FarmingView {
    static FarmingController controller = new FarmingController();
    public static boolean check(String input){
        Matcher matcher;
        if ((matcher = FarmingCommands.CraftInfo.getMatcher(input)) != null) {
            System.out.println(controller.craftInfo(matcher));
        } else if((matcher = FarmingCommands.Plant.getMatcher(input)) != null){
            System.out.println(controller.plant(matcher));
        } else if((matcher = FarmingCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(controller.showPlant(matcher));
        } else{
            return false;
        }
        return true;
    }
}
