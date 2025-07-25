package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.MapController;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class MapView {
    static MapController controller =  new MapController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.PrintMap.getMatcher(input))!=null){
            controller.printMap(matcher);
        }else if((matcher = GameMainCommands.Walk.getMatcher(input))!=null){
            System.out.println(controller.walk(matcher, null , null));
        }else if((matcher = GameMainCommands.HelpReadingMap.getMatcher(input))!=null){
            controller.helpReadingMap(matcher);
        }else if((matcher = GameMainCommands.PrintMapReal.getMatcher(input))!=null) {
            controller.printMapReal(matcher);
        }else if((matcher = GameMainCommands.PrintMapWhole.getMatcher(input))!=null) {
            controller.printWholeMap(matcher);
        }
        else{
            return false;
        }
        return true;
    }
}
