package com.CEliconValley.views.subGames;

import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.controllers.subgames.FarmingController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.tools.WateringCan;
import com.CEliconValley.views.commands.gameCommands.FarmingCommands;
import com.google.gson.Gson;

import java.util.regex.Matcher;

public class FarmingView {
    static FarmingController controller = new FarmingController();
    public static boolean check(String input, String playername){
        Matcher matcher;
        if ((matcher = FarmingCommands.CraftInfo.getMatcher(input)) != null) {
            System.out.println(controller.craftInfo(matcher));
        }else if ((matcher = FarmingCommands.GreenhouseBuild.getMatcher(input)) != null) {
            System.out.println(controller.buildGreenhouse(matcher));
        }else if((matcher = FarmingCommands.Plant.getMatcher(input)) != null){
            Result result = controller.plant(matcher, playername);
            System.out.println(result);
            if(!result.success()){
                GameMessage<ErrorMessage> response = new GameMessage<>("game_response",
                    new ErrorMessage("game_request", result.message()));
                App.getServer().sendToPlayername(playername, new Gson().toJson(response));
            }
        } else if((matcher = FarmingCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(controller.showPlant(matcher));
        } else if((matcher = FarmingCommands.HowMuchWater.getMatcher(input)) != null){
            if(App.getGame().getCurrentPlayer().getInventory().getSlotByItem(Finder.getToolByName("WateringCan")).getItem() instanceof WateringCan){
                System.out.println("Water left: " + ((WateringCan) App.getGame().getCurrentPlayer().getInventory().getSlotByItem(Finder.getToolByName("WateringCan")).getItem()).getTiles());
            }
        } else if((matcher = FarmingCommands.Fertilize.getMatcher(input)) != null){
            Result result = controller.fertilize(matcher, playername);
            System.out.println(result);
            App.sendResult(result, playername);
        }
        else{
            return false;
        }
        return true;
    }
}
