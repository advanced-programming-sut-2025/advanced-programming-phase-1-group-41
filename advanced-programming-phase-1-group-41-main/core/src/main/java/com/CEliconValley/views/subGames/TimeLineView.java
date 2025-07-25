package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.TimeLineController;
import com.CEliconValley.views.commands.gameCommands.WeatherAndTimeCommands;

import java.util.regex.Matcher;

public class TimeLineView {
    static TimeLineController controller =  new TimeLineController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = WeatherAndTimeCommands.Time.getMatcher(input))!=null){
            System.out.println(controller.getTime(matcher));
        }else if((matcher = WeatherAndTimeCommands.Date.getMatcher(input))!=null){
            System.out.println(controller.getDate(matcher));
        }else if((matcher = WeatherAndTimeCommands.DateTime.getMatcher(input))!=null){
            System.out.println(controller.getDateAndTime(matcher));
        }else if((matcher = WeatherAndTimeCommands.DayOfTheWeek.getMatcher(input))!=null){
            System.out.println(controller.getDayOfWeek(matcher));
        }else if((matcher = WeatherAndTimeCommands.Season.getMatcher(input))!=null){
            System.out.println(controller.getSeason(matcher));
        }else if((matcher = WeatherAndTimeCommands.CheatAdvanceTime.getMatcher(input))!=null){
            System.out.println(controller.cheatAdvanceTime(matcher));
        }else if((matcher = WeatherAndTimeCommands.CheatAdvanceDate.getMatcher(input))!=null){
            System.out.println(controller.cheatAdvanceDate(matcher));
        }else{
            return false;
        }
        return true;
    }
}
