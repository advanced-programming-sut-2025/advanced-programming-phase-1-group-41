package org.example.views.subGames;

import org.example.controllers.MapController;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.WeatherAndTimeCommands;

import java.util.regex.Matcher;

public class MapView {
    static MapController controller =  new MapController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.PrintMap.getMatcher(input))!=null){
            controller.printMap(matcher);
        }
        else{
            return false;
        }
        return true;
    }
}
