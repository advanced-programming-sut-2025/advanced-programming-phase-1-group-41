package org.example.views;

import org.example.controllers.AnimalController;
import org.example.controllers.TimeLineController;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.WeatherAndTimeCommands;

import java.util.regex.Matcher;

public class AnimalView {
    static AnimalController controller =  new AnimalController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.Build.getMatcher(input))!=null){
            System.out.println(controller.build(matcher));
        }else{
            return false;
        }
        return true;
    }
}
